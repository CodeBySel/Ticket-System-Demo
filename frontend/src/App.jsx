import { useState, useEffect } from 'react'
import axios from 'axios'
import { jsPDF } from 'jspdf'

function App() {
  const [user, setUser] = useState(null)
  const [username, setUsername] = useState('')
  const [seats, setSeats] = useState([])
  const [selectedSeats, setSelectedSeats] = useState([])

  const [movies, setMovies] = useState([])
  const [selectedMovieId, setSelectedMovieId] = useState(null)
  const [screenings, setScreenings] = useState([])
  const [selectedScreeningId, setSelectedScreeningId] = useState(null)

  const [firstName, setFirstName] = useState('')
  const [lastName, setLastName] = useState('')
  const [age, setAge] = useState('')

  const [receipt, setReceipt] = useState(null)
  const [step, setStep] = useState('selectMovie') // selectMovie -> booking -> receipt

  useEffect(() => {
    fetchMovies()
  }, [])

  useEffect(() => {
    if (selectedScreeningId) {
      fetchSeats(selectedScreeningId)
    }
  }, [selectedScreeningId])

  const fetchSeats = async (screeningId) => {
    try {
      const response = await axios.get(`/api/screenings/${screeningId}/seats`)
      setSeats(response.data)
    } catch (error) {
      console.error("Error fetching seats", error)
    }
  }

  const fetchScreenings = async (movieId) => {
    try {
      const response = await axios.get(`/api/screenings/movie/${movieId}`)
      setScreenings(response.data)
      if (response.data.length > 0) {
        setSelectedScreeningId(response.data[0].id ?? null)
      } else {
        setSelectedScreeningId(null)
      }
    } catch (error) {
      console.error("Error fetching screenings", error)
    }
  }

  const fetchMovies = async () => {
    try {
      const response = await axios.get('/api/movies')
      setMovies(response.data)
      if (response.data.length > 0) {
        setSelectedMovieId(response.data[0].id ?? null)
        fetchScreenings(response.data[0].id)
      }
    } catch (error) {
      console.error("Error fetching movies", error)
    }
  }

  const handleLogin = (e) => {
    e.preventDefault()
    if (username) {
      setUser({ id: 1, username: username })
      setStep('selectMovie')
    }
  }

  const toggleSeat = (seat) => {
    const seatStatus = String(seat.status || '').toUpperCase()
    if (seatStatus !== 'AVAILABLE') return

    if (selectedSeats.includes(seat.id)) {
      setSelectedSeats(selectedSeats.filter(id => id !== seat.id))
    } else {
      setSelectedSeats([...selectedSeats, seat.id])
    }
  }

  const handleBooking = async () => {
    if (!user) return alert("Please login first")
    if (!selectedMovieId) return alert("Please select a movie")
    if (!selectedScreeningId) return alert("Please select a screening time")
    if (!firstName || !lastName || !age) return alert("Please fill in your name and age")
    if (selectedSeats.length === 0) return alert("Please select at least one seat")

    try {
      const response = await axios.post('/api/bookings', {
        userId: user.id,
        seatIds: selectedSeats,
        screeningId: selectedScreeningId,
        firstName,
        lastName,
        age: Number(age),
        paymentMethod: "CARD"
      })

      const bookedTicket = response.data
      const movie = movies.find(m => m.id === selectedMovieId)
      const bookedSeats = seats.filter(seat => selectedSeats.includes(seat.id))

      setReceipt({
        ticketId: bookedTicket.bookingId,
        customerName: `${firstName} ${lastName}`,
        customerAge: age,
        movieTitle: movie?.title || 'Selected Movie',
        seats: bookedSeats.map(s => `${s.rowLabel}${s.seatNumber}`),
        perSeatPrice: 10,
        totalPrice: bookedTicket.totalPrice ?? selectedSeats.length * 10,
        bookingDate: bookedTicket.bookingDate
      })

      setSelectedSeats([])
      setFirstName('')
      setLastName('')
      setAge('')
      fetchSeats(selectedScreeningId)
      setStep('receipt')
    } catch (error) {
      alert("Booking Failed: " + (error.response?.data || error.message))
    }
  }

  const resetAndBookAgain = () => {
    setReceipt(null)
    setStep('selectMovie')
  }

  const handleNext = () => {
    if (step === 'selectMovie') {
      if (selectedMovieId) {
        setStep('booking')
      }
    } else if (step === 'booking') {
      // İleri butonu Confirm Booking ile aynı işlevi yapar
      handleBooking()
    }
  }

  const handleBack = () => {
    if (step === 'booking') {
      setStep('selectMovie')
    } else if (step === 'receipt') {
      setReceipt(null)
      setStep('booking')
    }
  }

  const canGoNext = () => {
    if (step === 'selectMovie') {
      return !!(selectedMovieId && selectedScreeningId)
    } else if (step === 'booking') {
      return !!(firstName && lastName && age && selectedSeats.length > 0)
    }
    return false
  }

  const canGoBack = () => {
    return step === 'booking' || step === 'receipt'
  }

  if (!user) {
    return (
      <div className="container">
        <h1>Cinema Login</h1>
        <form onSubmit={handleLogin}>
          <input
            type="text"
            placeholder="Username"
            value={username}
            onChange={e => setUsername(e.target.value)}
            style={{ padding: '10px', marginRight: '10px' }}
          />
          <button type="submit">Login</button>
        </form>
        <p>For demo, just enter any username.</p>
      </div>
    )
  }

  if (receipt) {
    const handleDownloadPdf = () => {
      const doc = new jsPDF()
      let y = 20

      doc.setFontSize(18)
      doc.text('Cinema Ticket Receipt', 20, y)
      y += 12

      doc.setFontSize(12)
      doc.text(`Ticket ID: ${receipt.ticketId}`, 20, y); y += 8
      doc.text(`Name: ${receipt.customerName} (${receipt.customerAge} years old)`, 20, y); y += 8
      doc.text(`Movie: ${receipt.movieTitle}`, 20, y); y += 8
      doc.text(`Seats: ${receipt.seats.join(', ')}`, 20, y); y += 8
      doc.text(`Price per Seat: $${receipt.perSeatPrice}`, 20, y); y += 8
      doc.text(`Total Price: $${receipt.totalPrice}`, 20, y); y += 8
      if (receipt.bookingDate) {
        doc.text(`Booking Time: ${new Date(receipt.bookingDate).toLocaleString()}`, 20, y); y += 8
      }
      doc.text('Sold by: Cinema Admin', 20, y)

      doc.save(`ticket-${receipt.ticketId}.pdf`)
    }

    return (
      <div className="container">
        <h1>Cinema Ticket Receipt</h1>
        <p><strong>Ticket ID:</strong> {receipt.ticketId}</p>
        <p><strong>Name:</strong> {receipt.customerName} ({receipt.customerAge} years old)</p>
        <p><strong>Movie:</strong> {receipt.movieTitle}</p>
        <p><strong>Seats:</strong> {receipt.seats.join(', ')}</p>
        <p><strong>Price per Seat:</strong> ${receipt.perSeatPrice}</p>
        <p><strong>Total Price:</strong> ${receipt.totalPrice}</p>
        {receipt.bookingDate && (
          <p><strong>Booking Time:</strong> {new Date(receipt.bookingDate).toLocaleString()}</p>
        )}
        <p><strong>Sold by:</strong> Cinema Admin</p>
        <div style={{ marginTop: '20px' }}>
          <div className="navigation-buttons">
            <button onClick={handleBack} style={{ marginRight: '10px' }}>
              ← Back
            </button>
            <button onClick={handleDownloadPdf} style={{ marginRight: '10px' }}>
              Download PDF
            </button>
            <button onClick={resetAndBookAgain}>Book Another Ticket</button>
          </div>
        </div>
      </div>
    )
  }

  const selectedMovie = movies.find(m => m.id === selectedMovieId)

  if (step === 'selectMovie') {
    return (
      <div className="container">
        <h1>Admin Movie Selection</h1>
        <p>Select a movie to open its booking screen.</p>

        <div className="movie-grid">
          {movies.map(movie => (
            <div
              key={movie.id}
              className={`movie-card ${selectedMovieId === movie.id ? 'selected' : ''}`}
              onClick={() => {
                setSelectedMovieId(movie.id)
                fetchScreenings(movie.id)
              }}
            >
              <div className="movie-poster">
                <span>{movie.title.charAt(0)}</span>
                {movie.posterUrl ? (
                  <img
                    src={movie.posterUrl}
                    alt={movie.title}
                    onError={(e) => {
                      e.currentTarget.style.display = 'none'
                    }}
                  />
                ) : null}
              </div>
              <h2>{movie.title}</h2>
              <p>{movie.genre}</p>
              <p>{movie.durationMinutes} min</p>
            </div>
          ))}
        </div>

        <div style={{ marginTop: '20px' }}>
          <label htmlFor="screeningSelect" style={{ marginRight: '10px' }}>
            Screening:
          </label>
          <select
            id="screeningSelect"
            value={selectedScreeningId ?? ''}
            onChange={(e) => setSelectedScreeningId(Number(e.target.value))}
          >
            {screenings.map((screening) => (
              <option key={screening.id} value={screening.id}>
                {new Date(screening.startTime).toLocaleString()}
              </option>
            ))}
          </select>
        </div>

        <div className="navigation-buttons" style={{ marginTop: '30px' }}>
          <button onClick={handleNext} disabled={!canGoNext()}>
            Next →
          </button>
        </div>
      </div>
    )
  }

  return (
    <div className="container">
      <h1>Cinema Seat Booking</h1>
      <p>Welcome, {user.username}</p>
      {selectedMovie && (
        <p>
          <strong>Movie:</strong> {selectedMovie.title} ({selectedMovie.genre}, {selectedMovie.durationMinutes} min)
        </p>
      )}

      <div style={{ marginBottom: '20px' }}>
        <input
          type="text"
          placeholder="First Name"
          value={firstName}
          onChange={e => setFirstName(e.target.value)}
          style={{ padding: '8px', marginRight: '10px' }}
        />
        <input
          type="text"
          placeholder="Last Name"
          value={lastName}
          onChange={e => setLastName(e.target.value)}
          style={{ padding: '8px', marginRight: '10px' }}
        />
        <input
          type="number"
          placeholder="Age"
          value={age}
          onChange={e => setAge(e.target.value)}
          style={{ padding: '8px', width: '80px' }}
          min="0"
        />
      </div>

      <div className="seat-grid">
        {seats.map(seat => (
          <div
            key={seat.id}
            className={`seat ${String(seat.status || '').toLowerCase()} ${selectedSeats.includes(seat.id) ? 'selected' : ''}`}
            onClick={() => toggleSeat(seat)}
          >
            {seat.rowLabel}{seat.seatNumber}
          </div>
        ))}
      </div>

      <div style={{ marginTop: '20px' }}>
        <p>Selected Seats: {selectedSeats.length}</p>
        <p>Total Price: ${selectedSeats.length * 10}</p>
        <div className="navigation-buttons">
          <button onClick={handleBack} style={{ marginRight: '10px' }}>
            ← Back
          </button>
          <button onClick={handleBooking} disabled={!canGoNext()}>
            Confirm Booking
          </button>
          <button onClick={handleNext} disabled={!canGoNext()} style={{ marginLeft: '10px' }}>
            Next →
          </button>
        </div>
      </div>
    </div>
  )
}

export default App
