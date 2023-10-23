import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

/*
* PROJE ÖNCÜLLERİ:
* PostgreSQL veritabanı paylaş
* Java Database Connector kütüphanesi kur
* GitHub collaboration
* Dbdiagram.io linki: https://dbdiagram.io/d/Yazlab-1-Database-Dizayni-652bc0f7ffbf5169f0b4f317
* */

// KENDİ ÖĞRENCİ TRANSKRİPTİN İLE YAPMAN LAZIM


public class App extends JFrame implements ActionListener, KeyListener {

  //---------------------------------------------UI BİLEŞENLERİ TANIMLAMA ALANI

  /*
  * Çalışma mantığı: Aplikasyonda kullanılacak tüm UI bileşenleri burada tanımlanır. Daha sonra
  * tanımlanan bu bileşenler GUIAtama() fonksiyonu içerisinde oluşturulur.
  * Ana JFrame'e bir panel JPanel'i eklenir.
  * Oluşturulan bu bileşenler farklı fonksiyonlarla panel'e doldurulur.
  * Ve panelde yapılan değişiklikler repaint() fonksiyonu ile panel yenilenerek
  * ana JFrame'de görülür.
  * */

  static String connectionURL = "jdbc:postgresql://localhost:5432/yazlab1?user=postgres&password=0000";

  JPanel panel;
  Connection connection;

  JButton yoneticiGirisEkraniButonu;
  JButton ogretmenGirisEkraniButonu;
  JButton ogrenciGirisEkraniButonu;
  JButton anaGirisEkraniDon;
  JButton yoneticiLoginButonu;
  JButton ogretmenLoginButonu;
  JButton ogrenciLoginButonu;

  JTextField yoneticiGirisSifreTextField;
  JTextField ogretmenGirisIsimTextField;
  JTextField ogretmenGirisSifreTextField;
  JTextField ogrenciGirisIsimTextField;
  JTextField ogrenciGirisSifreTextField;

  JLabel yoneticiGirisLabel;
  JLabel ogretmenGirisIsimLabel;
  JLabel ogretmenGirisSifreLabel;
  JLabel ogrenciGirisIsimLabel;
  JLabel ogrenciGirisSifreLabel;

  Color primary;
  Font mainFont;

  //---------------------------------------------UI BİLEŞENLERİ TANIMLAMA ALANI SONU



  //---------------------------------------------PRIVATE CLASS ALANI SONU
  private class Ogrenci{
    private Integer no;
    private String ad;
    private String soyad;
    private String sifre;
    private String[] ilgiAlanlari;
    private Float genelNot;

    public Ogrenci(Integer no, String ad, String soyad, String sifre, String[] ilgiAlanlari, Float genelNot) {
      this.no = no;
      this.ad = ad;
      this.soyad = soyad;
      this.sifre = sifre;
      this.ilgiAlanlari = ilgiAlanlari;
      this.genelNot = genelNot;
    }

    public Integer getNo() {
      return no;
    }

    public void setNo(Integer no) {
      this.no = no;
    }

    public String getAd() {
      return ad;
    }

    public void setAd(String ad) {
      this.ad = ad;
    }

    public String getSoyad() {
      return soyad;
    }

    public void setSoyad(String soyad) {
      this.soyad = soyad;
    }

    public String getSifre() {
      return sifre;
    }

    public void setSifre(String sifre) {
      this.sifre = sifre;
    }

    public String[] getIlgiAlanlari() {
      return ilgiAlanlari;
    }

    public void setIlgiAlanlari(String[] ilgiAlanlari) {
      this.ilgiAlanlari = ilgiAlanlari;
    }

    public Float getGenelNot() {
      return genelNot;
    }

    public void setGenelNot(Float genelNot) {
      this.genelNot = genelNot;
    }

  }

  private class Ogretmen{
    private Integer sicilNo;
    private String ad;
    private String soyad;
    private String sifre;
    private String[] ilgiAlanlari;
    private Integer kontenjanSayisi;
    private String[] acilanDersler;

    public Ogretmen(Integer sicilNo, String ad, String soyad, String sifre, String[] ilgiAlanlari, Integer kontenjanSayisi, String[] acilanDersler) {
      this.sicilNo = sicilNo;
      this.ad = ad;
      this.soyad = soyad;
      this.sifre = sifre;
      this.ilgiAlanlari = ilgiAlanlari;
      this.kontenjanSayisi = kontenjanSayisi;
      this.acilanDersler = acilanDersler;
    }

    public Integer getSicilNo() {
      return sicilNo;
    }

    public void setSicilNo(Integer sicilNo) {
      this.sicilNo = sicilNo;
    }

    public String getAd() {
      return ad;
    }

    public void setAd(String ad) {
      this.ad = ad;
    }

    public String getSoyad() {
      return soyad;
    }

    public void setSoyad(String soyad) {
      this.soyad = soyad;
    }

    public String getSifre() {
      return sifre;
    }

    public void setSifre(String sifre) {
      this.sifre = sifre;
    }

    public String[] getIlgiAlanlari() {
      return ilgiAlanlari;
    }

    public void setIlgiAlanlari(String[] ilgiAlanlari) {
      this.ilgiAlanlari = ilgiAlanlari;
    }

    public Integer getKontenjanSayisi() {
      return kontenjanSayisi;
    }

    public void setKontenjanSayisi(Integer kontenjanSayisi) {
      this.kontenjanSayisi = kontenjanSayisi;
    }

    public String[] getAcilanDersler() {
      return acilanDersler;
    }

    public void setAcilanDersler(String[] acilanDersler) {
      this.acilanDersler = acilanDersler;
    }
  }
  //---------------------------------------------PRIVATE CLASS TANIMLAMA ALANI SONU



  //---------------------------------------------GUI BÖLGESİ BAŞLANGICI

  void TextFieldTemizle(){
    yoneticiGirisSifreTextField.setText("");
    ogretmenGirisIsimTextField.setText("");
    ogretmenGirisSifreTextField.setText("");
    ogrenciGirisIsimTextField.setText("");
    ogrenciGirisSifreTextField.setText("");
  }

  JButton StandartGirisPaneliButonu(String baslik, int x, int y){
    JButton btn = new JButton(baslik);
    btn.setSize(300, 60);
    btn.setBorderPainted(false);
    btn.setBackground(primary);
    btn.setFont(mainFont);
    btn.setFocusable(false);
    btn.addActionListener(this);
    btn.setLocation(x, y);
    return btn;
  }

  JTextField StandartGirisPaneliTextField(int x, int y){
    JTextField textField = new JTextField();
    textField.setFont(mainFont);
    textField.setHorizontalAlignment(SwingConstants.LEFT);
    textField.setAlignmentY(CENTER_ALIGNMENT);
    textField.setSize(200, 40);
    textField.setLocation(x, y);
    textField.addKeyListener(this);
    return textField;
  }

  JLabel StandartGirisPaneliLabel(String text, int x, int y){
    JLabel label = new JLabel();
    label.setFont(mainFont);
    label.setText(text);
    label.setBounds(x, y, 500, 50);
    return label;
  }

  void GUIAtama(){
    panel = new JPanel();
    primary = new Color(147, 191, 207);
    mainFont = new Font("TimesRoman", Font.BOLD, 24);

    yoneticiGirisEkraniButonu = StandartGirisPaneliButonu("Yonetici Girisi", 450, 120);
    ogretmenGirisEkraniButonu = StandartGirisPaneliButonu("Ogretmen Girisi", 450, 330);
    ogrenciGirisEkraniButonu = StandartGirisPaneliButonu("Ogrenci Girisi", 450, 540);
    anaGirisEkraniDon = StandartGirisPaneliButonu("Geri don", 450, 680);

    yoneticiLoginButonu = StandartGirisPaneliButonu("Yonetici Olarak Gir", 450, 425);
    ogretmenLoginButonu = StandartGirisPaneliButonu("Ogretmen Olarak Gir", 450, 425);
    ogrenciLoginButonu = StandartGirisPaneliButonu("Ogrenci Olarak Gir", 450, 425);

    yoneticiGirisSifreTextField = StandartGirisPaneliTextField(500, 350);
    ogretmenGirisIsimTextField = StandartGirisPaneliTextField(500, 250);
    ogretmenGirisSifreTextField = StandartGirisPaneliTextField(500, 350);
    ogrenciGirisIsimTextField = StandartGirisPaneliTextField(500, 250);
    ogrenciGirisSifreTextField = StandartGirisPaneliTextField(500, 350);

    yoneticiGirisLabel = StandartGirisPaneliLabel("Yonetici sifresi giriniz:", 475, 300);
    ogretmenGirisIsimLabel = StandartGirisPaneliLabel("Ogretmen ismi ve soyismi giriniz:", 425, 200);
    ogretmenGirisSifreLabel = StandartGirisPaneliLabel("Ogretmen sifresi giriniz:", 475, 300);
    ogrenciGirisIsimLabel = StandartGirisPaneliLabel("Ogrenci ismi ve soyismi giriniz:", 425, 200);
    ogrenciGirisSifreLabel = StandartGirisPaneliLabel("Ogrenci sifresi giriniz:", 475, 300);

    panel.setLayout(null);
    this.add(panel);

    GirisEkrani();
  }

  //--------------UI Bileşenlerini Panele Doldurma

  void GirisEkrani() {
    panel.removeAll();
    TextFieldTemizle();
    panel.add(yoneticiGirisEkraniButonu);
    panel.add(ogretmenGirisEkraniButonu);
    panel.add(ogrenciGirisEkraniButonu);
    panel.repaint();

  /*
    try {
      while (resultSet.next()) {
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
          System.out.println(resultSet.getString(metaData.getColumnLabel(i));
        }
      }
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
  */
  }

  void YoneticiGirisEkrani(){
  panel.removeAll();
  TextFieldTemizle();
  panel.add(yoneticiGirisSifreTextField);
  panel.add(anaGirisEkraniDon);
  panel.add(yoneticiGirisLabel);
  panel.add(yoneticiLoginButonu);
  panel.repaint();
  }

  void OgretmenGirisEkrani(){
  panel.removeAll();
  TextFieldTemizle();
  panel.add(ogretmenGirisIsimTextField);
  panel.add(ogretmenGirisSifreTextField);
  panel.add(anaGirisEkraniDon);
  panel.add(ogretmenGirisIsimLabel);
  panel.add(ogretmenGirisSifreLabel);
  panel.add(ogretmenLoginButonu);
  panel.repaint();
  }

  void OgrenciGirisEkrani(){
  panel.removeAll();
  TextFieldTemizle();
  panel.add(ogrenciGirisIsimTextField);
  panel.add(ogrenciGirisSifreTextField);
  panel.add(anaGirisEkraniDon);
  panel.add(ogrenciGirisIsimLabel);
  panel.add(ogrenciGirisSifreLabel);
  panel.add(ogrenciLoginButonu);
  panel.repaint();
  }

  void OgrenciEkrani(Ogrenci ogrenci){
    panel.removeAll();
    //

    //

    panel.repaint();
  }

  //--------------UI Bileşenlerini Panele Doldurma Alani Sonu

  //---------------------------------------------GUI BÖLGESİ SONU



  App(Connection connection){
    this.connection = connection;
    this.setTitle("Ders Talep Sistemi");
    this.setSize(1200, 800);
    this.setResizable(false);
    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    GUIAtama();
    this.setVisible(true);
  }

  public static void main(String[] args) {
    try {
      new App(DriverManager.getConnection(connectionURL));
    } catch (SQLException e) {
      System.err.println("Bağlantı hatası.");
      e.printStackTrace();
    }
  }



  //---------------------------------------------GİRDİ FONKSİYONLARI BAŞLANGIÇI

  @Override
  public void actionPerformed(ActionEvent e) {
    if(e.getSource()== yoneticiGirisEkraniButonu){
      YoneticiGirisEkrani();
    }
    if(e.getSource()== ogretmenGirisEkraniButonu){
      OgretmenGirisEkrani();
    }
    if(e.getSource()== ogrenciGirisEkraniButonu){
      OgrenciGirisEkrani();
    }
    if(e.getSource()==anaGirisEkraniDon){
      GirisEkrani();
    }

    if(e.getSource()==yoneticiLoginButonu){
      try {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT admin_sifresi FROM parametreler WHERE id = 1");
        if (resultSet.next()){
          if (resultSet.getString("admin_sifresi").equals(yoneticiGirisSifreTextField.getText())){
            System.out.println("basarili");
          }
          else {
            System.out.println("sifre yanlis");
          }
        }
        else System.out.println("kayit yok");

      } catch (SQLException ex) {
        throw new RuntimeException(ex);
      }

    }
  }

  @Override
  public void keyTyped(KeyEvent e) {

  }

  @Override
  public void keyPressed(KeyEvent e) {

  }

  @Override
  public void keyReleased(KeyEvent e) {

  }

  //---------------------------------------------GİRDİ FONKSİYONLARI SONU
}
