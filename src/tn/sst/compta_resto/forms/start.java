/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.sst.compta_resto.forms;

import java.awt.EventQueue;
import tn.sst.grossiste.dto.emf.BaseDAO;

/**
 *
 * @author NEJI Walid
 */
public class start {

    /**
     * @param args the command line arguments
     */
//    private static MenuPrincipal menuPrincipal;
//
//    public static MenuPrincipal lancerMenuPrincipal() {
//        if (menuPrincipal == null) {
//            menuPrincipal = new MenuPrincipal();
//        }
//        return menuPrincipal;
//    }

    public static void main(String[] args) {
        // TODO code application logic here
        BaseDAO.createEMF();
             try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Gest_Article.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Gest_Article.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Gest_Article.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Gest_Article.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                 MenuPrincipal.getIsntance().setVisible(true);
            }
        });
//        MenuPrincipal.getIsntance().setVisible(true);
//        menuPrincipal.setVisible(true);


    }
}
