package presentation;

import lombok.Data;
import metier.CreditException;
import metier.iCreditMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

@Data
@Controller
public class CreditController implements iCreditController{
    @Autowired
    @Qualifier("metier")
    iCreditMetier creditMetier;
    @Override
    public void affcher_mensualite(Long idCredit) throws CreditException {
        System.out.println(creditMetier.calculerMensualite(idCredit));
    }
}
