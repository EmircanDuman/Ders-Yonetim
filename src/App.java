import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/*
* PROJE ÖNCÜLLERİ:
*
* PostgreSQL veritabanı paylaş
* Java Database Connector kütüphanesi kur
* GitHub collaboration
* Dbdiagram.io linki: https://dbdiagram.io/d/Yazlab-1-Database-Dizayni-652bc0f7ffbf5169f0b4f317
*
* */


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

  JPanel panel;

  JButton yoneticiGirisButonu;
  JButton ogretmenGirisButonu;
  JButton ogrenciGirisButonu;
  JButton anaGirisEkraniDon;
  JTextField yoneticiGirisSifreTextField;
  JTextField ogretmenGirisIsimTextField;
  JTextField ogretmenGirisSifreTextField;
  JTextField ogrenciGirisIsimTextField;
  JTextField ogrenciGirisSifreTextField;


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

  void GUIAtama(){
    panel = new JPanel();
    primary = new Color(147, 191, 207);
    mainFont = new Font("TimesRoman", Font.BOLD, 24);

    yoneticiGirisButonu = StandartGirisPaneliButonu("Yonetici Girisi", 450, 120);
    ogretmenGirisButonu = StandartGirisPaneliButonu("Ogretmen Girisi", 450, 330);
    ogrenciGirisButonu = StandartGirisPaneliButonu("Ogrenci Girisi", 450, 540);
    anaGirisEkraniDon = StandartGirisPaneliButonu("Geri don", 450, 680);

    yoneticiGirisSifreTextField = StandartGirisPaneliTextField(500, 350);
    ogretmenGirisIsimTextField = StandartGirisPaneliTextField(500, 250);
    ogretmenGirisSifreTextField = StandartGirisPaneliTextField(500, 350);
    ogrenciGirisIsimTextField = StandartGirisPaneliTextField(500, 250);
    ogrenciGirisSifreTextField = StandartGirisPaneliTextField(500, 350);

    panel.setLayout(null);
    this.add(panel);

    GirisEkrani();
  }

  //--------------UI Bileşenlerini Panele Doldurma

  void GirisEkrani(){
    panel.removeAll();
    panel.add(yoneticiGirisButonu);
    panel.add(ogretmenGirisButonu);
    panel.add(ogrenciGirisButonu);
    panel.repaint();
  }

  void YoneticiGirisEkrani(){
  panel.removeAll();
  panel.add(yoneticiGirisSifreTextField);
  panel.add(anaGirisEkraniDon);
  panel.repaint();
  }

  void OgretmenGirisEkrani(){
  panel.removeAll();
  panel.add(ogretmenGirisIsimTextField);
  panel.add(ogretmenGirisSifreTextField);
  panel.add(anaGirisEkraniDon);
  panel.repaint();
  }

  void OgrenciGirisEkrani(){
  panel.removeAll();
  panel.add(ogrenciGirisIsimTextField);
  panel.add(ogrenciGirisSifreTextField);
  panel.add(anaGirisEkraniDon);
  panel.removeAll();
  }

  //--------------UI Bileşenlerini Panele Doldurma Alani Sonu

  //---------------------------------------------GUI BÖLGESİ SONU



  App(){
    this.setTitle("Ders Talep Sistemi");
    this.setSize(1200, 800);
    this.setResizable(false);
    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    GUIAtama();
    this.setVisible(true);
  }

  public static void main(String[] args) {
    new App();
  }



  //---------------------------------------------GİRDİ FONKSİYONLARI BAŞLANGIÇI

  @Override
  public void actionPerformed(ActionEvent e) {
    if(e.getSource()==yoneticiGirisButonu){
      YoneticiGirisEkrani();
    }
    if(e.getSource()==ogretmenGirisButonu){
      OgretmenGirisEkrani();
    }
    if(e.getSource()==ogrenciGirisButonu){
      OgretmenGirisEkrani();
    }
    if(e.getSource()==anaGirisEkraniDon){
      GirisEkrani();
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
