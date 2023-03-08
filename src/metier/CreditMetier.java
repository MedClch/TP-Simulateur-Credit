package metier;

import dao.iDAO;
import lombok.Data;
import model.Credit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Data
@Service("metier")
public class CreditMetier implements iCreditMetier{
    @Autowired
    @Qualifier("dao")
    iDAO<Credit,Long> creditDAO;
    @Override
    public Credit calculerMensualite(Long idCredit) throws CreditException{
        var credit = creditDAO.trouverParID(idCredit);
        if(credit==null)
            throw new CreditException();
        else {
            double cp = credit.getCapitale_Empr();
            int nbMois = credit.getNb_Mois();
            double taux = credit.getTaux_Mensuel()/1200;
            double mens = (cp*taux)/(1-(Math.pow((1+taux),-1*nbMois)));
            mens = Math.round(mens*100)/100;
            credit.setMensualite(mens);
            return credit;
        }
    }
}
