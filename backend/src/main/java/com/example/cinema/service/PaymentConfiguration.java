package com.example.cinema.service;

import org.springframework.stereotype.Service;

/**
 * Provides payment gateway configuration values.
 */
@Service
public class PaymentConfiguration {
    /**
     * Returns the configured payment provider name.
     *
     * @return provider name
     */
    public String getProviderName() {
        return "DemoPaymentGateway";
    }

    /**
     * Returns the configured currency.
     *
     * @return currency code
     */
    public String getCurrency() {
        return "USD";
    }
}
