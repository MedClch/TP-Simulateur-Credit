package metier;

import model.Credit;

public interface iCreditMetier {
    Credit calculerMensualite(Long idCredit) throws CreditException;
}
