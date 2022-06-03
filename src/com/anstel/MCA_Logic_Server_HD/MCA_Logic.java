package com.anstel.MCA_LOGIC_Server_HD;

/**
 * Classe qui demande à InIn une mise en relation entre un agent et un
 * correspondant.
 *
 * @author Thierry Baribaud
 * @version 3.0.0.
 */

public class MCA_Logic {

    private String key = "";
    private String noTelephone = "";
    private String clientRef = "";

    MCA_Logic(String noTelephone, String login, String clientRef) {
        this.key = login;
        this.noTelephone = noTelephone;
        this.clientRef = clientRef;
    }

    MCA_Logic(String noTelephone, String login) {
        this.key = login;
        this.noTelephone = noTelephone;
        this.clientRef = "COFELY";
    }

//    public static void main(String[] args) {
//        MCA_Logic mca_Logic;
//
//        switch (args.length) {
//            case 2:
//                mca_Logic = new MCA_Logic(args[0], args[1]);
//                mca_Logic.Run();
//                break;
//            case 3:
//                mca_Logic = new MCA_Logic(args[0], args[1], args[2]);
//                mca_Logic.Run();
//                break;
//            default:
//                System.out.println("Usage : java MCA_Logic no_tel login client_ref");
//                break;
//        }
//    }

    /**
     * Make the call
     */
    public void Run() {
        System.out.println("Trying to reach " + noTelephone + " for " + key + " ...");
    }
}
