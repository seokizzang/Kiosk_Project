
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author ShinHyeonSeo
 */
public class OrderingFrame extends javax.swing.JFrame {
    public static OrderingFrame Instance;
    private String[] menuName;
    DB_MAN DBM = new DB_MAN(); 
    JPanel menuListPanel = new JPanel(new FlowLayout());
    
    /**
     * Creates new form OrderingFrame
     */
    public OrderingFrame() {
        initComponents();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Instance = this;
        menuListPanel.setSize(497, 485);
        menuListPanel.setBackground(Color.white);
        mainPanel.add(menuListPanel);
    }
    
    public final void createSoldLine(String menu,int price){
        // Add menu name and price to the panel
        JPanel itemList = new JPanel(new GridLayout(0, 3));
        JPanel itemPanel = new JPanel(new BorderLayout());
        JLabel menuLabel = new JLabel(" "+menu);
        JLabel soldLabel = new JLabel("1");
        JLabel priceLabel = new JLabel(String.format("%,d \u20A9  ",price));
        JButton delButton = new JButton("X");
        
        itemPanel.setBackground(Color.white);
        itemList.setBackground(Color.white);
        itemList.setPreferredSize(new Dimension(497,30));
        menuLabel.setFont(new Font("Serif", Font.PLAIN, 18));
        menuLabel.setHorizontalAlignment(JLabel.LEFT);
        soldLabel.setFont(new Font("Serif", Font.PLAIN, 18));
        soldLabel.setHorizontalAlignment(JLabel.CENTER);
        priceLabel.setFont(new Font("Serif", Font.PLAIN, 18));
        priceLabel.setHorizontalAlignment(JLabel.RIGHT);
        delButton.setPreferredSize(new Dimension(35,35));
        delButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                itemList.setVisible(false);
                String[] deltedArry = new String[menuName.length-1];
                for(int i=0;i<menuName.length;i++){
                    if(menuName[i].equals(menu)){
                        System.out.println(menu);
                        System.out.println(menuName[i]);
                    System.arraycopy(menuName,0,deltedArry,0,i);
                    System.arraycopy(menuName, i+1, deltedArry, i, menuName.length-i-1);
                    menuName = deltedArry;
                    break;
                    }
                }
                
            }
        });
        
        itemPanel.add(delButton,BorderLayout.WEST);
        itemPanel.add(menuLabel,BorderLayout.CENTER);
        itemList.add(itemPanel);
        itemList.add(soldLabel);
        itemList.add(priceLabel);
        menuListPanel.add(itemList);
    }
    
    public final void getOrderData(String menu){
        menuName =  menu.split(",");
        String strQuery;
        
        int price;
        for(int i=0;i<menuName.length;i++){
            strQuery = "Select menu_price from Summary where menu_name = ";
            System.out.println(menuName[i].trim());
            String menuEng = menunameToEng(menuName[i].trim());
                System.out.println(menuEng);
            String addMenuSold = "Update Summary Set ";
            addMenuSold += "Menu_sold = Menu_sold+1";
            addMenuSold += " WHERE Summary.Menu_name = '"+menuEng+"'";
            
            strQuery += "'"+menuEng+"'";
            try{
                DBM.dbOpen();
                DBM.DB_rs = DBM.DB_stmt.executeQuery(strQuery);
                while(DBM.DB_rs.next()){
                price = DBM.DB_rs.getInt("Menu_price");
                createSoldLine(menuName[i].trim(),price);
                }
                DBM.dbClose();
            }catch(Exception e){
                System.out.println("getOrderData Except" + e.getMessage());            
            }
        }
    }
    
    public final void addOrderData(String[] menu){
        for(int i=0;i<menu.length;i++){
            String menuEng = menunameToEng(menu[i].trim());
            String addMenuSold = "Update Summary Set ";
            addMenuSold += "Menu_sold = Menu_sold+1";
            addMenuSold += " WHERE Summary.Menu_name = '"+menuEng+"'";
   
            try{
                DBM.dbOpen();
                DBM.DB_rs = DBM.DB_stmt.executeQuery(addMenuSold);
                DBM.dbClose();
            }catch(Exception e){
                System.out.println("getOrderData Except" + e.getMessage());            
            }
        }
    }
    
        public String menunameToKor(String menuName){
        String korName;
        switch (menuName.toLowerCase()){
            case "bibimbap": korName = "비빔밥"; break;
            case "jeyuk" : korName = "제육볶음";break;
            case "kimchijjigae" : korName = "김치찌개";break;
            case "bulgogi" : korName="불고기";break;
            case "jjazang" : korName="짜장면";break;
            case "friedrice" : korName="볶음밥";break;
            case "tangsuyuk" : korName ="탕수육";break;
            case "ramen" : korName = "라멘";break;
            case "porkcutlet" : korName = "돈까스";break;
            case "sushi" : korName = "스시";break;
            case "donburi" : korName ="돈부리";break;
            case "coke" : korName = "콜라";break;
            case "sprite" : korName = "사이다";break;
            case "fantaoran" : korName = "환타오렌지";break;
            case "fantagrap" : korName = "환타포도";break;
            case "jjampong" : korName = "짬뽕";break;
            default : korName = "DB에 없는값";break;
        }
        return korName;
    }
    
    public String menunameToEng(String menuName){
        String engName;
        switch (menuName.toLowerCase()){
            case "비빔밥": engName = "bibimbap"; break;
            case "제육볶음": engName = "jeyuk";break;
            case "김치찌개" : engName = "kimchijjigae";break;
            case "불고기" : engName="bulgogi";break;
            case "짜장면" : engName="jjazang";break;
            case "볶음밥" : engName="friedrice";break;
            case "탕수육" : engName ="tangsuyuk";break;
            case "라멘" : engName = "ramen";break;
            case "돈까스" : engName = "porkcutlet";break;
            case "스시" : engName = "sushi";break;
            case "돈부리" : engName ="donburi";break;
            case "콜라" : engName = "coke";break;
            case "사이다" : engName = "sprite";break;
            case "환타오렌지" : engName = "fantaoran";break;
            case "환타포도" : engName = "fantagrap";break;
            case "짬뽕" : engName = "jjampong";break;
            default : engName = "DB에 없는값";break;
        }
        return engName;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        mainPanel = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("궁서체", 1, 36)); // NOI18N
        jLabel1.setText("주문서");

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 486, Short.MAX_VALUE)
        );

        jButton1.setFont(new java.awt.Font("굴림", 1, 36)); // NOI18N
        jButton1.setText("결             제");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 488, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(188, 188, 188)
                .addComponent(jLabel1)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(70, 70, 70)
                .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(this, "결제가 완료되었습니다. 감사합니다!", "결제 완료", JOptionPane.INFORMATION_MESSAGE);
        addOrderData(menuName);
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(OrderingFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(OrderingFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(OrderingFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(OrderingFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new OrderingFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel mainPanel;
    // End of variables declaration//GEN-END:variables
}
