import dao.CreditDAO;
import dao.iDAO;
import metier.CreditException;
import metier.CreditMetier;
import metier.iCreditMetier;
import model.Credit;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import presentation.CreditController;
import presentation.iCreditController;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Properties;
import java.util.Scanner;

public class SimulateurCredit_App {
    static iCreditController creditController;
    static Scanner sc = new Scanner(System.in);
    private static boolean isNumeric(String value){
        try {
            Integer.parseInt(value);
            return true;
        } catch(Exception e){
            return false;
        }
    }
    public static void test1() {
        //fabrique
        var dao = new CreditDAO();
        var metier = new CreditMetier();
        creditController = new CreditController();

        //injection des dependances
        ((CreditMetier)metier).setCreditDAO(dao);
        ((CreditController)creditController).setCreditMetier(metier);

        //test
        String rep="";

        do{
//            System.out.println("=> Test 1 : Calcul de mesualite de credit n°");
            System.out.println("=> Test 1 : Calcul de mesualite de credit");
            try {
                String input;
                while(true){
                    System.out.print("| Donner l'id du credit : ");
                    input = sc.next();
                    if(isNumeric(input)) break;
                    System.out.println("| Erreur : Entree non valide !!");
                }
                Long id = Long.parseLong(input);
                creditController.affcher_mensualite(id);
            } catch (CreditException e) {
                e.printStackTrace();
            }
            System.out.print("=> Voulez vous quitter ? (oui/non) --> ");
            rep = sc.next();
            System.out.println("\n");
        }while(!rep.equalsIgnoreCase("oui"));
    }

    public static void test2() throws Exception {
        String daoClass;
        String metierClass;
        String controllerClass;
        Properties properties = new Properties();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream propFile = classLoader.getResourceAsStream("config.properties");
        if(propFile==null) throw new Exception("| ERREUR : Fichier config introuvable !!\n");
        else{
            try{
                properties.load(propFile);
                daoClass = properties.getProperty("DAO");
                metierClass = properties.getProperty("SERVICE");
                controllerClass = properties.getProperty("CONTROLLER");
                propFile.close();
            }catch (IOException e){
                throw new Exception("| ERREUR : Probleme de chargement des proprietes du fichier config !!\n");
            }
            finally {
                properties.clear();
            }

            try{
                Class cDAO = Class.forName(daoClass);
                Class cMetier = Class.forName(metierClass);
                Class cController = Class.forName(controllerClass);
                var dao = (iDAO<Credit,Long>)cDAO.getDeclaredConstructor().newInstance();
                var metier = (iCreditMetier)cMetier.getDeclaredConstructor().newInstance();
                creditController = (iCreditController)cController.getDeclaredConstructor().newInstance();
                Method setDAO = cMetier.getMethod("setCreditDAO",iDAO.class);
                setDAO.invoke(metier,dao);
                Method setMetier = cController.getMethod("setCreditMetier",iCreditMetier.class);
                setMetier.invoke(creditController,metier);
                creditController.affcher_mensualite(2L);
            }catch (CreditException e) {
                e.printStackTrace();
            }
        }
    }

    public static void test3() throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-ioc.xml");
        creditController = (iCreditController) context.getBean("controleur");
        try {
            creditController.affcher_mensualite(2L);
        } catch (CreditException e) {
            throw new RuntimeException(e);
        }
    }

    public static void test4() throws Exception {
        ApplicationContext context = new AnnotationConfigApplicationContext("dao","metier","presentation");
        creditController = (iCreditController) context.getBean("controleur");
        try {
            creditController.affcher_mensualite(2L);
        } catch (CreditException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws Exception{
//        test1();
//        test2();
        test3();
//        test4();
    }
}