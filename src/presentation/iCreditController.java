package presentation;

import metier.CreditException;

public interface iCreditController {
    void affcher_mensualite(Long idCredit) throws CreditException;
}
