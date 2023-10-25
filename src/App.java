import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

// KENDİ ÖĞRENCİ TRANSKRİPTİN İLE YAPMAN LAZIM


public class App extends JFrame implements ActionListener, KeyListener {

  //---------------------------------------------UI BİLEŞENLERİ TANIMLAMA ALANI

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
  JButton yoneticiParametreleriKaydetmeButonu;
  JButton rastgeleOgrenciOlusturButonu;
  JButton yoneticiTalepleriListeleButonu;
  JButton yoneticiOgretmenListeleButonu;
  JButton yoneticiOgrenciListeleButonu;

  JComboBox<String> yoneticiDurumComboBox;

  JCheckBox ayniHocaMultCheckBox;

  JSpinner ayniDersMaksTalepSpinner;
  JSpinner talepMaksKarakterSpinner;
  JSpinner rastgeleOgrenciOlusturSpinner;

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
  JLabel ayniDersMaksTalepLabel;
  JLabel talepMaksKarakterLabel;
  JLabel rastgeleOgrenciOlusturLabel;

  Color primary;
  Font mainFont;

  //---------------------------------------------UI BİLEŞENLERİ TANIMLAMA ALANI SON

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
    panel.setLayout(null);
    primary = new Color(147, 191, 207);
    mainFont = new Font("TimesRoman", Font.BOLD, 24);

    yoneticiGirisEkraniButonu = StandartGirisPaneliButonu("Yonetici Girisi", 450, 120);
    ogretmenGirisEkraniButonu = StandartGirisPaneliButonu("Ogretmen Girisi", 450, 330);
    ogrenciGirisEkraniButonu = StandartGirisPaneliButonu("Ogrenci Girisi", 450, 540);
    anaGirisEkraniDon = StandartGirisPaneliButonu("Geri don", 450, 680);
    yoneticiTalepleriListeleButonu = StandartGirisPaneliButonu("Talepleri Listele", 550, 250);
    yoneticiOgretmenListeleButonu = StandartGirisPaneliButonu("Ogretmenleri Listele", 550, 400);
    yoneticiOgrenciListeleButonu = StandartGirisPaneliButonu("Ogrencileri Listele", 550, 550);

    yoneticiLoginButonu = StandartGirisPaneliButonu("Yonetici Olarak Gir", 450, 425);
    ogretmenLoginButonu = StandartGirisPaneliButonu("Ogretmen Olarak Gir", 450, 425);
    ogrenciLoginButonu = StandartGirisPaneliButonu("Ogrenci Olarak Gir", 450, 425);
    yoneticiParametreleriKaydetmeButonu = StandartGirisPaneliButonu("Kaydet", 80,600);
    rastgeleOgrenciOlusturButonu = StandartGirisPaneliButonu("Olustur", 870, 84);

    ayniHocaMultCheckBox = new JCheckBox("Ayni hocadan cok sayida ders");
    ayniHocaMultCheckBox.setBounds(60, 218, 450, 60);
    ayniHocaMultCheckBox.setFont(mainFont);

    yoneticiDurumComboBox = new JComboBox<String>(new String[]{"beklemede", "kabul", "iptal", "ret"});
    yoneticiDurumComboBox.setBounds(80, 84, 300, 60);
    yoneticiDurumComboBox.setFont(mainFont);
    yoneticiDurumComboBox.setBackground(Color.white);

    SpinnerNumberModel spinnerModel = new SpinnerNumberModel(1, 0, 10, 1);
    ayniDersMaksTalepSpinner = new JSpinner(spinnerModel);
    ayniDersMaksTalepSpinner.setBounds(80, 362, 300, 60);
    ayniDersMaksTalepSpinner.setFont(mainFont);

    SpinnerModel spinnerModel1 = new SpinnerNumberModel(100, 0, 1000, 10);
    talepMaksKarakterSpinner = new JSpinner(spinnerModel1);
    talepMaksKarakterSpinner.setBounds(80, 512, 300, 60);
    talepMaksKarakterSpinner.setFont(mainFont);

    SpinnerModel spinnerModel2 = new SpinnerNumberModel(1, 1, 50, 5);
    rastgeleOgrenciOlusturSpinner = new JSpinner(spinnerModel2);
    rastgeleOgrenciOlusturSpinner.setBounds(550, 84, 300, 60);
    rastgeleOgrenciOlusturSpinner.setFont(mainFont);

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
    ayniDersMaksTalepLabel = StandartGirisPaneliLabel("Ayni ders maks talep sayisi", 80, 320);
    talepMaksKarakterLabel = StandartGirisPaneliLabel("Talep mesaji maks karakter sayisi", 60, 470);
    rastgeleOgrenciOlusturLabel = StandartGirisPaneliLabel("Rastgele ogrenci olustur", 550, 40);
    rastgeleOgrenciOlusturButonu.setSize(200, 60);

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

  // Talep durumu / aynı hoca mult / ayni ders mult / talep maks karakter / ilgi alanlari
  // Öğrenciler listelenmeli / detaylı seceneği / düzenleme / ilgi alanlari belirleme
  // Öğretmenler listelenmeli / detaylı seçeneği / düzenleme / dersleri açma
  // rastgele öğrenci olusturma
  // tüm öğrenci ve hocalari silme ? ya da öyle bir şey

  void YoneticiEkrani(){
  panel.removeAll();
  panel.add(yoneticiDurumComboBox);
  panel.add(ayniHocaMultCheckBox);
  panel.add(ayniDersMaksTalepSpinner);
  panel.add(ayniDersMaksTalepLabel);
  panel.add(talepMaksKarakterSpinner);
  panel.add(talepMaksKarakterLabel);
  panel.add(yoneticiParametreleriKaydetmeButonu);
  panel.add(rastgeleOgrenciOlusturSpinner);
  panel.add(rastgeleOgrenciOlusturLabel);
  panel.add(rastgeleOgrenciOlusturButonu);
  panel.add(yoneticiTalepleriListeleButonu);
  panel.add(yoneticiOgretmenListeleButonu);
  panel.add(yoneticiOgrenciListeleButonu);
  panel.add(anaGirisEkraniDon);
  panel.repaint();
  }

  void OgretmenEkrani(Ogretmen ogretmen){

  }

  void OgrenciEkrani(Ogrenci ogrenci){

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
          if (resultSet.getString("admin_sifresi").equals(yoneticiGirisSifreTextField.getText().trim())){
            YoneticiEkrani();
          }
          else {
            JOptionPane.showMessageDialog(this, "Lutfen dogru sifreyi girin.");
          }
        }
        else JOptionPane.showMessageDialog(this, "Kayit yok");

        statement.close();
        resultSet.close();
      } catch (SQLException ex) {
        throw new RuntimeException(ex);
      }
    }
    if (e.getSource() == ogretmenLoginButonu) {
      try {
        String[] adSoyadArray = ogretmenGirisIsimTextField.getText().trim().split("\\s+");
        if(adSoyadArray.length != 2){
          JOptionPane.showMessageDialog(this, "Lutfen dogru formatta girin.");
          return;
        }
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM hocalar WHERE ad = ? AND soyad = ? AND sifre= ?");
        statement.setString(1, adSoyadArray[0]);
        statement.setString(2, adSoyadArray[1]);
        statement.setString(3, ogretmenGirisSifreTextField.getText().trim());

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
          OgretmenEkrani(new Ogretmen(resultSet.getInt(1), resultSet.getString(3),
          resultSet.getString(4), resultSet.getString(2),
          (String[]) resultSet.getArray(5).getArray(), resultSet.getInt(6),
          (String[]) resultSet.getArray(7).getArray()));
        } else {
          JOptionPane.showMessageDialog(this, "Giris basarisiz.");
        }
        statement.close();
        resultSet.close();
      } catch (SQLException ex) {
          throw new RuntimeException(ex);
        }
      }
    if (e.getSource() == ogrenciLoginButonu) {
      try {
        String[] adSoyadArray = ogrenciGirisIsimTextField.getText().trim().split("\\s+");
        if(adSoyadArray.length != 2){
          JOptionPane.showMessageDialog(this, "Lutfen dogru formatta girin.");
          return;
        }
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM ogrenciler WHERE ad = ? AND soyad = ? AND sifre= ?");
        statement.setString(1, adSoyadArray[0]);
        statement.setString(2, adSoyadArray[1]);
        statement.setString(3, ogrenciGirisSifreTextField.getText().trim());
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
          OgrenciEkrani(new Ogrenci(resultSet.getInt(1), resultSet.getString(3),
          resultSet.getString(4), resultSet.getString(2),
          (String[]) resultSet.getArray(5).getArray(), resultSet.getFloat(7)));
        } else {
          JOptionPane.showMessageDialog(this, "Giris basarisiz.");
        }
        statement.close();
        resultSet.close();
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
