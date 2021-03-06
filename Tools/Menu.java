/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tools;

import java.io.IOException;
import static java.lang.Thread.sleep;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author zeze
 */
public class Menu extends Thread{
   
    
    @Override
    public void run(){
        try {
            menu();
        } catch (IOException | InterruptedException | ClassNotFoundException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void menu() throws IOException, InterruptedException, ClassNotFoundException {
        
        clearScreen();
       

        System.out.print("Bem vindo à Aplicação de troca de Ficheiros\n"
                + "___________________________________________\n\n"
                + "Escolha por favor uma das seguintes opcoes:\n"
                + "1 - Listar Ficheiros \n"
                + "2 - Tranferir Ficheiro\n"
                + "9 - Informações\n"
                + "0 - Sair\n"
                + "___________________________________________\n\nOpcao: ");
                

        Scanner ler = new Scanner(System.in);
        char opcao = ler.next().charAt(0);
        ler.reset();
        switch (opcao) {
            case '1':
                clearScreen();
                System.out.println(ListHandler.globalToString() + "\n");
                System.out.println("Prima qualquer tecla para voltar ao menu...");
                System.in.read();
                clearScreen();
                menu();
                
                break;   
            case '2':
                clearScreen();
                System.out.println(
                        ListHandler.globalToString() 
                        + "\n" 
                        +"Escolha o ficheiro que quer transferir: "
                );
                int num = ler.nextInt();
                MessageHandler.askFile(
                        ListHandler.getGlobalFileList()[num - 1][0],
                        ListHandler.getGlobalFileList()[num - 1][1]
                );
                System.out.println("Prima qualquer tecla para voltar ao menu...");
                System.in.read();
                menu();
                break;
            case '9':
                infor();
                menu();
                
                break;
            case '0':
                clearScreen();
                System.out.println("Saiu da aplicação, até a próxima.");
                System.exit(0);
                
                break;
            default:
                  System.out.println("Este não é um dia válido!");
                  menu();
        }

    }
    public void infor() throws InterruptedException, IOException{
        clearScreen();
        System.out.println("_______________________________________________________\n\n"
                           +"Aplicação Realizada no Ambito da unidade curricular de\nSistemas Distribuidos\n"
                           +"8120248 - Telmo Pinto Pinto - 8120248@estgf.ipp.pt\n"  
                           +"8120135 - José Ricardo Pinto - 8120135@estgf.ipp.pt\n" 
                           +"______________________________________________________\n"
                           
        );
        
        sleep(3000);
        
    } 
    public static void clearScreen() {  
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
   }  
    
    
}
  