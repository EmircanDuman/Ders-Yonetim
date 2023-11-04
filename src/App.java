import org.apache.pdfbox.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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
  JButton yoneticiIlgiAlanlariListeleButonu;
  JButton yoneticiGeriButonu;
  JButton yoneticiIlgiAlaniEkleButonu;
  JButton yoneticiIlgiAlaniSilButonu;
  JButton ogretmenTalepleriListeleButonu;
  JButton ogretmenOgrencileriListeleButonu;
  JButton ogretmenDersleriGoruntuleButonu;
  JButton TalepKabulEtButonu;
  JButton TalepReddetButonu;
  JButton yoneticiTabloGeriButonu;
  JButton yoneticiDersleriListeleButonu;
  JButton yoneticiDersEkleButonu;
  JButton yoneticiDersSilButonu;
  JButton ogretmenDersiAlButonu;
  JButton ogretmenDersiBirakButonu;
  JButton ogretmenIlgiAlanlariListeleButonu;
  JButton ogretmenIlgiAlaniEkleButonu;
  JButton ogretmenIlgiAlaniSilButonu;
  JButton ogrenciIlgiAlanlariButonu;
  JButton ogrenciPDFYukleButonu;
  JButton ogrenciDersleriGoruntuleButonu;
  JButton ogrenciIlgiAlaniEkleButonu;
  JButton ogrenciIlgiAlaniSilButonu;
  JButton ogrenciTalepGonderButonu;
  JButton ogrenciTalepSilButonu;

  JComboBox<String> yoneticiDurumComboBox;

  JTable table;

  JCheckBox ayniHocaMultCheckBox;

  JSpinner ayniDersMaksTalepSpinner;
  JSpinner talepMaksKarakterSpinner;
  JSpinner rastgeleOgrenciOlusturSpinner;

  JTextField yoneticiGirisSifreTextField;
  JTextField ogretmenGirisIsimTextField;
  JTextField ogretmenGirisSifreTextField;
  JTextField ogrenciGirisIsimTextField;
  JTextField ogrenciGirisSifreTextField;
  JTextField yoneticiIlgiAlaniEkleTextField;
  JTextField yoneticiDersEkleTextField;

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

  Ogretmen ogretmen;
  Ogrenci ogrenci;

  enum TalepDurumu{
    anlasma,
    rastgele,
    durdur
  }

  enum DersTalepDurumu{
    beklemede,
    kabul,
    iptal,
    ret
  }

  //---------------------------------------------UI BİLEŞENLERİ TANIMLAMA ALANI SON

  //---------------------------------------------PRIVATE CLASS ALANI SONU
  private static class Ogrenci{
    public Integer no;
    public String ad;
    public String soyad;
    public String sifre;
    public String[] ilgiAlanlari;
    public Float genelNot;

    public Ogrenci(Integer no, String ad, String soyad, String sifre, String[] ilgiAlanlari, Float genelNot) {
      this.no = no;
      this.ad = ad;
      this.soyad = soyad;
      this.sifre = sifre;
      this.ilgiAlanlari = ilgiAlanlari;
      this.genelNot = genelNot;
    }
  }

  private static class Ogretmen{
    public Integer sicilNo;
    public String ad;
    public String soyad;
    public String sifre;
    public String[] ilgiAlanlari;
    public Integer kontenjanSayisi;
    public String[] acilanDersler;

    public Ogretmen(Integer sicilNo, String ad, String soyad, String sifre, String[] ilgiAlanlari, Integer kontenjanSayisi, String[] acilanDersler) {
      this.sicilNo = sicilNo;
      this.ad = ad;
      this.soyad = soyad;
      this.sifre = sifre;
      this.ilgiAlanlari = ilgiAlanlari;
      this.kontenjanSayisi = kontenjanSayisi;
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
    yoneticiTalepleriListeleButonu = StandartGirisPaneliButonu("Talepleri Listele", 500, 250);
    yoneticiOgretmenListeleButonu = StandartGirisPaneliButonu("Ogretmenleri Listele", 500, 400);
    yoneticiOgrenciListeleButonu = StandartGirisPaneliButonu("Ogrencileri Listele", 500, 550);
    yoneticiIlgiAlanlariListeleButonu = StandartGirisPaneliButonu("Ilgi Alanlari Listele", 850, 250);
    yoneticiDersleriListeleButonu = StandartGirisPaneliButonu("Dersleri Listele", 850, 400);
    yoneticiGeriButonu = StandartGirisPaneliButonu("Geri", 450, 600);
    yoneticiIlgiAlaniEkleButonu = StandartGirisPaneliButonu("Ekle", 885, 160);
    yoneticiIlgiAlaniEkleButonu.setSize(200, 60);
    yoneticiIlgiAlaniSilButonu = StandartGirisPaneliButonu("Sil", 885, 250);
    yoneticiIlgiAlaniSilButonu.setSize(200, 60);

    yoneticiDersEkleButonu = StandartGirisPaneliButonu("Ekle", 885, 160);
    yoneticiDersEkleButonu.setSize(200, 60);
    yoneticiDersSilButonu = StandartGirisPaneliButonu("Sil", 885, 250);
    yoneticiDersSilButonu.setSize(200, 60);

    TalepKabulEtButonu = StandartGirisPaneliButonu("Kabul Et", 70, 500);
    TalepReddetButonu = StandartGirisPaneliButonu("Reddet", 70, 600);

    ogretmenIlgiAlaniEkleButonu = StandartGirisPaneliButonu("Ilgi Alani Ekle", 70, 500);
    ogretmenIlgiAlaniSilButonu = StandartGirisPaneliButonu("Ilgi Alani Sil", 70, 600);

    ogretmenDersiAlButonu = StandartGirisPaneliButonu("Dersi Al", 70, 500);
    ogretmenDersiBirakButonu = StandartGirisPaneliButonu("Dersi Birak", 70, 600);
    ogretmenTalepleriListeleButonu = StandartGirisPaneliButonu("Talepleri Listele", 70, 100);
    ogretmenOgrencileriListeleButonu = StandartGirisPaneliButonu("Ogrencileri Listele", 70, 200);
    ogretmenDersleriGoruntuleButonu = StandartGirisPaneliButonu("Dersleri Goruntule", 70, 300);
    ogretmenIlgiAlanlariListeleButonu = StandartGirisPaneliButonu("Ilgi Alanlari", 70, 400);
    yoneticiTabloGeriButonu = StandartGirisPaneliButonu("Geri", 70, 100);

    ogrenciIlgiAlanlariButonu = StandartGirisPaneliButonu("Ilgi Alanlari", 70, 100);
    ogrenciDersleriGoruntuleButonu = StandartGirisPaneliButonu("Dersleri Goruntule", 70, 200);
    ogrenciPDFYukleButonu = StandartGirisPaneliButonu("PDF Yukle", 70, 200);
    ogrenciIlgiAlaniEkleButonu = StandartGirisPaneliButonu("Ilgi Alani Ekle", 70, 500);
    ogrenciIlgiAlaniSilButonu = StandartGirisPaneliButonu("Ilgi Alani Sil", 70, 600);
    ogrenciTalepGonderButonu = StandartGirisPaneliButonu("Talep Gonder", 70, 500);
    ogrenciTalepSilButonu = StandartGirisPaneliButonu("Talep Sil", 70, 600);

    yoneticiLoginButonu = StandartGirisPaneliButonu("Yonetici Olarak Gir", 450, 425);
    ogretmenLoginButonu = StandartGirisPaneliButonu("Ogretmen Olarak Gir", 450, 425);
    ogrenciLoginButonu = StandartGirisPaneliButonu("Ogrenci Olarak Gir", 450, 425);
    yoneticiParametreleriKaydetmeButonu = StandartGirisPaneliButonu("Kaydet", 80,600);
    rastgeleOgrenciOlusturButonu = StandartGirisPaneliButonu("Olustur", 870, 84);

    ayniHocaMultCheckBox = new JCheckBox("Ayni hocadan cok sayida ders");
    ayniHocaMultCheckBox.setBounds(60, 218, 400, 60);
    ayniHocaMultCheckBox.setFont(mainFont);

    yoneticiDurumComboBox = new JComboBox<>(new String[]{"anlasma", "rastgele", "durdur"});
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
    yoneticiIlgiAlaniEkleTextField = StandartGirisPaneliTextField(890, 100);
    yoneticiDersEkleTextField = StandartGirisPaneliTextField(890, 100);

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

  void OgrenciEkrani(Ogrenci ogrenci){
    this.ogrenci = ogrenci;
    OgrenciIlgiAlaniGoruntule();
  }

  void OgrenciIlgiAlaniGoruntule(){
    try{
      PreparedStatement ogrenciPreparedStatement = connection.prepareStatement("SELECT ilgi_alanlari FROM ogrenciler WHERE ogrenci_no = ?");
      ogrenciPreparedStatement.setInt(1, ogrenci.no);
      Statement parametrelerStatement = connection.createStatement();

      ResultSet ogrenciResultSet = ogrenciPreparedStatement.executeQuery();
      ResultSet parametrelerResultSet = parametrelerStatement.executeQuery("SELECT ilgi_alanlari FROM parametreler WHERE id = 1");

      DefaultTableModel model = new DefaultTableModel();
      model.addColumn("Ilgi Alani");
      model.addColumn("Ilgi Alani Durumu");

      ogrenciResultSet.next();
      parametrelerResultSet.next();

      String[] ogrenciIlgiAlanlari = (String[]) ogrenciResultSet.getArray(1).getArray();
      String[] parametreIlgiAlanlari = (String[]) parametrelerResultSet.getArray(1).getArray();

      for (String ilgiAlani : parametreIlgiAlanlari) {
        if (Arrays.asList(ogrenciIlgiAlanlari).contains(ilgiAlani)) {
          model.addRow(new Object[]{ilgiAlani, "Dahil"});
        } else {
          model.addRow(new Object[]{ilgiAlani, "Dahil Degil"});
        }
      }

      table = new JTable(model);
      table.setFont(mainFont);
      table.setRowHeight(60);
      JScrollPane scrollPane = new JScrollPane(table);
      scrollPane.setBounds(450, 100, 600, 600);
      panel.removeAll();

      PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM notlar WHERE ogrenci_no = ?");
      preparedStatement.setInt(1, ogrenci.no);
      ResultSet resultSet = preparedStatement.executeQuery();
      if(resultSet.next()) panel.add(ogrenciDersleriGoruntuleButonu);
      else panel.add(ogrenciPDFYukleButonu);

      panel.add(scrollPane);
      panel.add(ogrenciIlgiAlanlariButonu);
      panel.add(ogrenciIlgiAlaniEkleButonu);
      panel.add(ogrenciIlgiAlaniSilButonu);

      panel.repaint();
    }
    catch (SQLException ex){
      throw new RuntimeException(ex);
    }
  }

  void OgrenciDersleriGoruntule(){
    try{
      JScrollPane scrollPane = new JScrollPane();

      Statement ogretmenStatement = connection.createStatement();
      ResultSet ogretmenResultSet = ogretmenStatement.executeQuery("SELECT * FROM hocalar");

      PreparedStatement ogrenciPreparedStatement = connection.prepareStatement("SELECT * FROM ogrenciler WHERE ogrenci_no = ?");
      ogrenciPreparedStatement.setInt(1, ogrenci.no);

      ResultSet ogrenciResultSet = ogrenciPreparedStatement.executeQuery();
      ogrenciResultSet.next();

      List<String> ogrenciIlgiList = Arrays.asList((String[]) ogrenciResultSet.getArray(5).getArray());

      DefaultTableModel model = new DefaultTableModel();
      model.addColumn("Ogretmen No");
      model.addColumn("Ad Soyad");
      model.addColumn("Ders Adı");
      model.addColumn("Talep Durumu");
      model.addColumn("Ilgi Alanlari");

      while(ogretmenResultSet.next()){

        List<String> ogretmenIlgiList = Arrays.asList((String[]) ogretmenResultSet.getArray(5).getArray());
        List<String> copyOfOgrenciIlgiList = new ArrayList<>(ogrenciIlgiList);
        copyOfOgrenciIlgiList.retainAll(ogretmenIlgiList);

        if (!copyOfOgrenciIlgiList.isEmpty()){

          String[] ogretmenDersArray = (String[]) ogretmenResultSet.getArray(5).getArray();

          for(String ders : ogretmenDersArray){
            PreparedStatement miniPPS = connection.prepareStatement("SELECT * FROM anlasmalar WHERE ogrenci_no = ? AND ogretmen_no = ? AND ders = ?");
            miniPPS.setInt(1, ogrenci.no);
            miniPPS.setInt(2, ogretmenResultSet.getInt(1));
            miniPPS.setString(3, ders);
            ResultSet miniRS = miniPPS.executeQuery();

            if(miniRS.next()){
              switch (DersTalepDurumu.valueOf(miniRS.getString(8))){
                case ret -> {
                  model.addRow(new Object[]{ogretmenResultSet.getInt(1), (String)(ogretmenResultSet.getString(3)+" "+ogretmenResultSet.getString(4))
                          , ders, "Ret", String.join(", ", (String[])ogretmenResultSet.getArray(5).getArray())});
                }
                case kabul -> {
                  model.addRow(new Object[]{ogretmenResultSet.getInt(1), (String)(ogretmenResultSet.getString(3)+" "+ogretmenResultSet.getString(4))
                          , ders, "Kabul", String.join(", ", (String[])ogretmenResultSet.getArray(5).getArray())});
                }
                case beklemede -> {
                  model.addRow(new Object[]{ogretmenResultSet.getInt(1), (String)(ogretmenResultSet.getString(3)+" "+ogretmenResultSet.getString(4))
                          , ders, "Beklemede", String.join(", ", (String[])ogretmenResultSet.getArray(5).getArray())});
                }
                case iptal -> {
                  model.addRow(new Object[]{ogretmenResultSet.getInt(1), (String)(ogretmenResultSet.getString(3)+" "+ogretmenResultSet.getString(4))
                          , ders, "Iptal", String.join(", ", (String[])ogretmenResultSet.getArray(5).getArray())});
                }
              }
            }
            else {
              model.addRow(new Object[]{ogretmenResultSet.getInt(1), (String)(ogretmenResultSet.getString(3)+" "+ogretmenResultSet.getString(4))
                      , ders, "Talep Yok", String.join(", ", (String[])ogretmenResultSet.getArray(5).getArray())});
            }
          }
        }
      }
      table = new JTable(model);
      table.setRowHeight(60);
      table.setFont(mainFont);

      scrollPane.getViewport().add(table);

      scrollPane.setBounds(450, 100, 600, 600);

      panel.removeAll();
      panel.add(scrollPane);
      panel.add(ogrenciIlgiAlanlariButonu);

      PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM notlar WHERE ogrenci_no = ?");
      preparedStatement.setInt(1, ogrenci.no);
      ResultSet resultSet = preparedStatement.executeQuery();
      if(resultSet.next()){
        panel.add(ogrenciDersleriGoruntuleButonu);
        panel.add(ogrenciTalepGonderButonu);
        panel.add(ogrenciTalepSilButonu);
      }
      else panel.add(ogrenciPDFYukleButonu);

      panel.repaint();
    }
    catch (SQLException ex){
      throw new RuntimeException(ex);
    }
  }

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
    panel.add(yoneticiIlgiAlanlariListeleButonu);
    panel.add(yoneticiDersleriListeleButonu);
    panel.add(anaGirisEkraniDon);
    try {
      PreparedStatement statement = connection.prepareStatement("SELECT * FROM parametreler WHERE id = 1");
      ResultSet resultSet = statement.executeQuery();
      if(resultSet.next()){
        switch (TalepDurumu.valueOf(resultSet.getString(2))){
          case anlasma -> yoneticiDurumComboBox.setSelectedIndex(0);
          case rastgele -> yoneticiDurumComboBox.setSelectedIndex(1);
          case durdur -> yoneticiDurumComboBox.setSelectedIndex(2);
        }
        ayniHocaMultCheckBox.setSelected(resultSet.getBoolean(3));

        ayniDersMaksTalepSpinner.setValue(resultSet.getInt(4));
        talepMaksKarakterSpinner.setValue(resultSet.getInt(5));
      }

      statement.close();
      resultSet.close();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    panel.repaint();
  }

  void DersleriListeleEkrani(){
    panel.removeAll();
    try {
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery("SELECT dersler FROM parametreler WHERE id = 1");
      if(resultSet.next()){
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Dersler");
        for(String ilgiAlani : (String[]) resultSet.getArray(1).getArray()){
          model.addRow(new Object[]{ilgiAlani});
        }
        table = new JTable(model);
        table.setRowHeight(60);
        table.setFont(mainFont);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(400, 70, 400, 500);
        panel.add(scrollPane);
        panel.add(yoneticiGeriButonu);
        panel.add(yoneticiDersEkleButonu);
        panel.add(yoneticiDersEkleTextField);
        panel.add(yoneticiDersSilButonu);

        statement.close();
        resultSet.close();
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    panel.repaint();
  }

  void IlgiAlanlariListeleEkrani(){
    panel.removeAll();
    try {
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery("SELECT ilgi_alanlari FROM parametreler WHERE id = 1");
      if(resultSet.next()){
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Ilgi Alanlari");
        for(String ilgiAlani : (String[]) resultSet.getArray(1).getArray()){
          model.addRow(new Object[]{ilgiAlani});
        }
        table = new JTable(model);
        table.setRowHeight(60);
        table.setFont(mainFont);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(400, 70, 400, 500);
        panel.add(scrollPane);
        panel.add(yoneticiGeriButonu);
        panel.add(yoneticiIlgiAlaniEkleButonu);
        panel.add(yoneticiIlgiAlaniEkleTextField);
        panel.add(yoneticiIlgiAlaniSilButonu);

        statement.close();
        resultSet.close();
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    panel.repaint();
  }

  void OgretmenEkrani(Ogretmen ogretmen){
    panel.removeAll();
    this.ogretmen = ogretmen;

    OgretmenEkraniTalepListele(ogretmen);

    panel.repaint();
  }

  void OgretmenEkraniTalepListele(Ogretmen ogretmen){
    try {
      PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM anlasmalar WHERE ogretmen_no = ?");
      preparedStatement.setInt(1, ogretmen.sicilNo);
      ResultSet resultSet = preparedStatement.executeQuery();

      DefaultTableModel model = new DefaultTableModel();
      model.addColumn("Anlasma No");
      model.addColumn("Ogrenci No");
      model.addColumn("Hoca No");
      model.addColumn("Ders");
      model.addColumn("Durum");
      while(resultSet.next()){
        model.addRow(new Object[]{resultSet.getInt(1), resultSet.getInt(2), resultSet.getInt(3), resultSet.getString(4), DersTalepDurumu.valueOf(resultSet.getString(8)).toString()});
      }
      table = new JTable(model);
      table.setFont(mainFont);
      table.setRowHeight(60);
      JScrollPane scrollPane = new JScrollPane(table);
      scrollPane.setBounds(450, 100, 600, 600);

      panel.removeAll();

      panel.add(ogretmenTalepleriListeleButonu);
      panel.add(ogretmenOgrencileriListeleButonu);
      panel.add(ogretmenDersleriGoruntuleButonu);
      panel.add(ogretmenIlgiAlanlariListeleButonu);
      panel.add(scrollPane);
      panel.add(TalepKabulEtButonu);
      panel.add(TalepReddetButonu);
      panel.repaint();
    }
    catch (SQLException ex){
      throw new RuntimeException(ex);
    }
  }

  void YoneticiTalepleriListele(){
    try {
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery("SELECT * FROM anlasmalar");

      DefaultTableModel model = new DefaultTableModel();
      model.addColumn("Anlasma No");
      model.addColumn("Ogrenci No");
      model.addColumn("Hoca No");
      model.addColumn("Ders");
      model.addColumn("Durum");
      while(resultSet.next()){
        model.addRow(new Object[]{resultSet.getInt(1), resultSet.getInt(2), resultSet.getInt(3), resultSet.getString(4), DersTalepDurumu.valueOf(resultSet.getString(8)).toString()});
      }
      table = new JTable(model);
      table.setFont(mainFont);
      table.setRowHeight(60);
      JScrollPane scrollPane = new JScrollPane(table);
      scrollPane.setBounds(450, 100, 600, 600);

      panel.removeAll();

      panel.add(scrollPane);
      panel.add(yoneticiTabloGeriButonu);
      panel.add(TalepKabulEtButonu);
      panel.add(TalepReddetButonu);
      panel.repaint();
    }
    catch (SQLException ex){
      throw new RuntimeException(ex);
    }
  }

  void OgretmenOgrencileriListele(){
    try {
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery("SELECT * FROM ogrenciler");

      PreparedStatement preparedStatement = connection.prepareStatement("SELECT ilgi_alanlari FROM hocalar WHERE sicil_no = ?");
      preparedStatement.setInt(1, ogretmen.sicilNo);
      ResultSet resultSet1 = preparedStatement.executeQuery();

      DefaultTableModel model = new DefaultTableModel();
      model.addColumn("Ogrenci No");
      model.addColumn("Ad");
      model.addColumn("Soyad");
      model.addColumn("Ilgi Alanlari");
      resultSet1.next();
      while (resultSet.next()){
        List<String> intersection = new ArrayList<>();
        Set<String> set1 = new HashSet<>(Arrays.asList((String[]) resultSet1.getArray(1).getArray()));
        for (String element : (String[]) resultSet.getArray(5).getArray()) {
          if (set1.contains(element)) {
            intersection.add(element);
          }
        }
        String[] ortakIlgiAlanlari = intersection.toArray(new String[0]);
        if(ortakIlgiAlanlari.length != 0){
          model.addRow(new Object[]{resultSet.getInt(1), resultSet.getString(3), resultSet.getString(4), String.join(", ", ortakIlgiAlanlari)});
        }
      }

      resultSet1.close();
      resultSet.close();
      statement.close();
      preparedStatement.close();

      table = new JTable(model);
      table.setFont(mainFont);
      table.setRowHeight(60);
      JScrollPane scrollPane = new JScrollPane(table);
      scrollPane.setBounds(450, 100, 600, 600);

      panel.removeAll();
      panel.add(ogretmenTalepleriListeleButonu);
      panel.add(ogretmenIlgiAlanlariListeleButonu);
      panel.add(ogretmenOgrencileriListeleButonu);
      panel.add(ogretmenDersleriGoruntuleButonu);

      panel.add(scrollPane);
      panel.repaint();
    }
    catch (SQLException ex){
      throw new RuntimeException(ex);
    }
  }

  void OgretmenDersleriGoruntule(Ogretmen ogretmen){
    try{
      PreparedStatement ogretmenPreparedStatement = connection.prepareStatement("SELECT dersler FROM hocalar WHERE sicil_no = ?");
      ogretmenPreparedStatement.setInt(1, ogretmen.sicilNo);
      Statement parametrelerStatement = connection.createStatement();

      ResultSet ogretmenResultSet = ogretmenPreparedStatement.executeQuery();
      ResultSet parametrelerResultSet = parametrelerStatement.executeQuery("SELECT dersler FROM parametreler WHERE id = 1");

      DefaultTableModel model = new DefaultTableModel();
      model.addColumn("Ders");
      model.addColumn("Ders Durumu");

      ogretmenResultSet.next();
      parametrelerResultSet.next();

      String[] ogretmenDersler = (String[]) ogretmenResultSet.getArray(1).getArray();
      String[] parametreDersler = (String[]) parametrelerResultSet.getArray(1).getArray();

      for (String ders : parametreDersler) {
        if (Arrays.asList(ogretmenDersler).contains(ders)) {
          model.addRow(new Object[]{ders, "Dersi Veriyor"});
        } else {
          model.addRow(new Object[]{ders, "Dersi Vermiyor"});
        }
      }

      table = new JTable(model);
      table.setFont(mainFont);
      table.setRowHeight(60);
      JScrollPane scrollPane = new JScrollPane(table);
      scrollPane.setBounds(450, 100, 600, 600);

      panel.removeAll();
      panel.add(ogretmenTalepleriListeleButonu);
      panel.add(ogretmenOgrencileriListeleButonu);
      panel.add(ogretmenDersleriGoruntuleButonu);
      panel.add(ogretmenIlgiAlanlariListeleButonu);
      panel.add(ogretmenDersiAlButonu);
      panel.add(ogretmenDersiBirakButonu);
      panel.add(scrollPane);
      panel.repaint();
    }
    catch (SQLException ex){
      throw new RuntimeException(ex);
    }
  }

  void OgretmenIlgiAlanlariListele(){
    try{
      PreparedStatement ogretmenPreparedStatement = connection.prepareStatement("SELECT ilgi_alanlari FROM hocalar WHERE sicil_no = ?");
      ogretmenPreparedStatement.setInt(1, ogretmen.sicilNo);
      Statement parametrelerStatement = connection.createStatement();

      ResultSet ogretmenResultSet = ogretmenPreparedStatement.executeQuery();
      ResultSet parametrelerResultSet = parametrelerStatement.executeQuery("SELECT ilgi_alanlari FROM parametreler WHERE id = 1");

      DefaultTableModel model = new DefaultTableModel();
      model.addColumn("Ilgi Alani");
      model.addColumn("Ilgi Alani Durumu");

      ogretmenResultSet.next();
      parametrelerResultSet.next();

      String[] ogretmenIlgiAlanlari = (String[]) ogretmenResultSet.getArray(1).getArray();
      String[] parametreIlgiAlanlari = (String[]) parametrelerResultSet.getArray(1).getArray();

      for (String ilgiAlani : parametreIlgiAlanlari) {
        if (Arrays.asList(ogretmenIlgiAlanlari).contains(ilgiAlani)) {
          model.addRow(new Object[]{ilgiAlani, "Dahil"});
        } else {
          model.addRow(new Object[]{ilgiAlani, "Dahil Degil"});
        }
      }

      table = new JTable(model);
      table.setFont(mainFont);
      table.setRowHeight(60);
      JScrollPane scrollPane = new JScrollPane(table);
      scrollPane.setBounds(450, 100, 600, 600);

      panel.removeAll();
      panel.add(ogretmenTalepleriListeleButonu);
      panel.add(ogretmenOgrencileriListeleButonu);
      panel.add(ogretmenDersleriGoruntuleButonu);
      panel.add(ogretmenIlgiAlanlariListeleButonu);
      panel.add(ogretmenIlgiAlaniEkleButonu);
      panel.add(ogretmenIlgiAlaniSilButonu);
      panel.add(scrollPane);
      panel.repaint();
    }
    catch (SQLException ex){
      throw new RuntimeException(ex);
    }
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
    if(e.getSource() == yoneticiGirisEkraniButonu){
      YoneticiGirisEkrani();
    }
    if(e.getSource() == ogretmenGirisEkraniButonu){
      OgretmenGirisEkrani();
    }
    if(e.getSource() == ogrenciGirisEkraniButonu){
      OgrenciGirisEkrani();
    }
    if(e.getSource() ==anaGirisEkraniDon){
      GirisEkrani();
    }
    if(e.getSource() == yoneticiLoginButonu){
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
    if(e.getSource() == ogretmenLoginButonu) {
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
    if(e.getSource() == ogrenciLoginButonu) {
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
    if(e.getSource() == yoneticiParametreleriKaydetmeButonu){
      try {
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE public.parametreler " +
          "SET durum=?, ayni_hoca_mult=?, ayni_ders_mult=?, talep_maks_karakter=? " + "WHERE id = 1");

        preparedStatement.setObject(1, yoneticiDurumComboBox.getSelectedItem(), Types.OTHER);
        preparedStatement.setBoolean(2,ayniHocaMultCheckBox.isSelected());
        preparedStatement.setInt(3, (Integer) ayniDersMaksTalepSpinner.getValue());
        preparedStatement.setInt(4, (Integer) talepMaksKarakterSpinner.getValue());

        preparedStatement.executeUpdate();

        preparedStatement.close();

      } catch (SQLException ex) {
        throw new RuntimeException(ex);
      }
    }
    if(e.getSource() == yoneticiIlgiAlanlariListeleButonu){
      IlgiAlanlariListeleEkrani();
    }
    if(e.getSource() == yoneticiGeriButonu || e.getSource() == yoneticiTabloGeriButonu){
      YoneticiEkrani();
    }
    if(e.getSource() == yoneticiIlgiAlaniEkleButonu){
      try {
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE parametreler SET ilgi_alanlari = ? WHERE id = 1");
        if (!yoneticiIlgiAlaniEkleTextField.getText().trim().equals("")) {
          Statement statement = connection.createStatement();
          ResultSet resultSet = statement.executeQuery("SELECT ilgi_alanlari FROM parametreler WHERE id = 1");
          if (resultSet.next()) {
            if(Arrays.asList((String[]) resultSet.getArray(1).getArray()).contains(yoneticiIlgiAlaniEkleTextField.getText())) return;

            String[] strings = (String[]) resultSet.getArray(1).getArray();
            String[] strings1 = new String[strings.length + 1];
            System.arraycopy(strings, 0, strings1, 0, strings.length);
            strings1[strings1.length - 1] = yoneticiIlgiAlaniEkleTextField.getText().trim().toUpperCase();
            Array sqlArray = connection.createArrayOf("text", strings1);
            preparedStatement.setArray(1, sqlArray);
            preparedStatement.executeUpdate();

            preparedStatement.close();
            statement.close();
            resultSet.close();

            yoneticiIlgiAlaniEkleTextField.setText("");
            IlgiAlanlariListeleEkrani();
          }
        }
      } catch (SQLException ex) {
        throw new RuntimeException(ex);
      }
    }
    if(e.getSource() == yoneticiIlgiAlaniSilButonu && table.getSelectedRow() != -1){
      try{
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT ilgi_alanlari FROM parametreler WHERE id = 1");
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE parametreler SET ilgi_alanlari = ? WHERE id = 1");
        if (resultSet.next()){
          String[] strings = (String[]) resultSet.getArray(1).getArray();

          ArrayList<String> stringList = new ArrayList<>(Arrays.asList(strings));
          stringList.remove(table.getValueAt(table.getSelectedRow(), 0));
          strings = stringList.toArray(new String[0]);
          Array sqlArray = connection.createArrayOf("text", strings);
          preparedStatement.setArray(1, sqlArray);
          preparedStatement.executeUpdate();

          statement.close();
          resultSet.close();
          preparedStatement.close();

          Statement statement1 = connection.createStatement();
          ResultSet resultSet1 = statement1.executeQuery("SELECT * FROM hocalar");
          PreparedStatement preparedStatement1 = connection.prepareStatement("UPDATE hocalar SET ilgi_alanlari = ? WHERE sicil_no = ?");
          while (resultSet1.next()){
            String[] strings1 = (String[]) resultSet1.getArray(5).getArray();
            ArrayList<String> stringList1 = new ArrayList<>(Arrays.asList(strings1));
            stringList1.remove(table.getValueAt(table.getSelectedRow(), 0));
            strings1 = stringList1.toArray(new String[0]);
            Array sqlArray1 = connection.createArrayOf("text", strings1);
            preparedStatement1.setArray(1, sqlArray1);
            preparedStatement1.setInt(2, resultSet1.getInt(1));
            preparedStatement1.executeUpdate();
          }

          statement1.close();
          resultSet1.close();
          preparedStatement1.close();

          IlgiAlanlariListeleEkrani();
        }
      }
      catch (SQLException ex){
        throw new RuntimeException(ex);
      }
    }
    if(e.getSource() == yoneticiDersEkleButonu){
      try {
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE parametreler SET dersler = ? WHERE id = 1");
        if (!yoneticiDersEkleTextField.getText().trim().equals("")) {
          Statement statement = connection.createStatement();
          ResultSet resultSet = statement.executeQuery("SELECT dersler FROM parametreler WHERE id = 1");
          if (resultSet.next()) {
            if(Arrays.asList((String[]) resultSet.getArray(1).getArray()).contains(yoneticiDersEkleTextField.getText())) return;

            String[] strings = (String[]) resultSet.getArray(1).getArray();
            String[] strings1 = new String[strings.length + 1];
            System.arraycopy(strings, 0, strings1, 0, strings.length);
            strings1[strings1.length - 1] = yoneticiDersEkleTextField.getText().trim().toUpperCase();
            Array sqlArray = connection.createArrayOf("text", strings1);
            preparedStatement.setArray(1, sqlArray);
            preparedStatement.executeUpdate();

            preparedStatement.close();
            statement.close();
            resultSet.close();

            yoneticiDersEkleTextField.setText("");
            DersleriListeleEkrani();
          }
        }
      } catch (SQLException ex) {
        throw new RuntimeException(ex);
      }
    }
    if(e.getSource() == yoneticiDersSilButonu && table.getSelectedRow() != -1){
      try{
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT dersler FROM parametreler WHERE id = 1");
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE parametreler SET dersler = ? WHERE id = 1");
        if (resultSet.next()){
          String[] strings = (String[]) resultSet.getArray(1).getArray();

          ArrayList<String> stringList = new ArrayList<>(Arrays.asList(strings));
          stringList.remove(table.getValueAt(table.getSelectedRow(), 0));
          strings = stringList.toArray(new String[0]);
          Array sqlArray = connection.createArrayOf("text", strings);
          preparedStatement.setArray(1, sqlArray);
          preparedStatement.executeUpdate();

          statement.close();
          resultSet.close();
          preparedStatement.close();

          Statement statement1 = connection.createStatement();
          ResultSet resultSet1 = statement1.executeQuery("SELECT * FROM hocalar");
          PreparedStatement preparedStatement1 = connection.prepareStatement("UPDATE hocalar SET dersler = ? WHERE sicil_no = ?");
          while (resultSet1.next()){
            String[] strings1 = (String[]) resultSet1.getArray(5).getArray();
            ArrayList<String> stringList1 = new ArrayList<>(Arrays.asList(strings1));
            stringList1.remove(table.getValueAt(table.getSelectedRow(), 0));
            strings1 = stringList1.toArray(new String[0]);
            Array sqlArray1 = connection.createArrayOf("text", strings1);
            preparedStatement1.setArray(1, sqlArray1);
            preparedStatement1.setInt(2, resultSet1.getInt(1));
            preparedStatement1.executeUpdate();
          }

          statement1.close();
          resultSet1.close();
          preparedStatement1.close();

          PreparedStatement preparedStatement2 = connection.prepareStatement("DELETE FROM anlasmalar WHERE ders = ?");
          preparedStatement2.setString(1, (String) table.getValueAt(table.getSelectedRow(), 0));
          preparedStatement2.executeUpdate();

          preparedStatement2.close();

          DersleriListeleEkrani();
        }
      }
      catch (SQLException ex){
        throw new RuntimeException(ex);
      }
    }
    if(e.getSource() == ogretmenTalepleriListeleButonu){
      OgretmenEkraniTalepListele(this.ogretmen);
    }
    if(e.getSource() == TalepKabulEtButonu && table.getSelectedRow() != -1){
      try{
        Statement stat = connection.createStatement();
        ResultSet resset = stat.executeQuery("SELECT * FROM parametreler");
        resset.next();
        if(!TalepDurumu.valueOf(resset.getString(2)).equals(TalepDurumu.anlasma)){
          JOptionPane.showMessageDialog(this, "Anlasma asamasi degil.");
          return;
        }
        stat.close();
        resset.close();
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE anlasmalar SET durum = ? WHERE anlasma_no = ?");
        preparedStatement.setObject(1, DersTalepDurumu.kabul, Types.OTHER);
        preparedStatement.setInt(2, (Integer) table.getValueAt(table.getSelectedRow(), 1));
        preparedStatement.executeUpdate();
        table.setValueAt("kabul", table.getSelectedRow(), 4);

        panel.repaint();
        preparedStatement.close();
      }
      catch (SQLException ex){
        throw new RuntimeException(ex);
      }
    }
    if(e.getSource() == TalepReddetButonu && table.getSelectedRow() != -1){
      try{
        Statement stat = connection.createStatement();
        ResultSet resset = stat.executeQuery("SELECT * FROM parametreler");
        resset.next();
        if(!TalepDurumu.valueOf(resset.getString(2)).equals(TalepDurumu.anlasma)){
          JOptionPane.showMessageDialog(this, "Anlasma asamasi degil.");
          return;
        }
        stat.close();
        resset.close();
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE anlasmalar SET durum = ? WHERE anlasma_no = ?");
        preparedStatement.setObject(1, DersTalepDurumu.ret, Types.OTHER);
        preparedStatement.setInt(2, (Integer) table.getValueAt(table.getSelectedRow(), 1));
        preparedStatement.executeUpdate();
        table.setValueAt("ret", table.getSelectedRow(), 4);

        panel.repaint();
        preparedStatement.close();
      }
      catch (SQLException ex){
        throw new RuntimeException(ex);
      }
    }
    if(e.getSource() == ogretmenOgrencileriListeleButonu){
      OgretmenOgrencileriListele();
    }
    if(e.getSource() == yoneticiTalepleriListeleButonu){
      YoneticiTalepleriListele();
    }
    if(e.getSource() == yoneticiDersleriListeleButonu){
      DersleriListeleEkrani();
    }
    if(e.getSource() == ogretmenDersleriGoruntuleButonu){
      OgretmenDersleriGoruntule(ogretmen);
    }
    if(e.getSource() == ogretmenDersiAlButonu){
      try{
        if(table.getSelectedRow() == -1) return;

        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE hocalar SET dersler = ? WHERE sicil_no = ?");
        preparedStatement.setInt(2, ogretmen.sicilNo);

        PreparedStatement preparedStatement1 = connection.prepareStatement("SELECT dersler FROM hocalar WHERE sicil_no = ?");
        preparedStatement1.setInt(1, ogretmen.sicilNo);

        ResultSet resultSet1 = preparedStatement1.executeQuery();
        resultSet1.next();

        String[] dizi = (String[]) resultSet1.getArray(1).getArray();
        for (String eleman : dizi) {
          if (eleman.equals(table.getValueAt(table.getSelectedRow(), 0))) {
            return;
          }
        }

        String[] strings = (String[]) resultSet1.getArray(1).getArray();

        String[] strings1 = new String[strings.length + 1];
        System.arraycopy(strings, 0, strings1, 0, strings.length);
        strings1[strings1.length - 1] = (String) table.getValueAt(table.getSelectedRow(), 0);
        Array sqlArray = connection.createArrayOf("text", strings1);
        preparedStatement.setArray(1, sqlArray);
        preparedStatement.executeUpdate();

        table.setValueAt("Dersi Veriyor", table.getSelectedRow(), 1);
      }
      catch (SQLException ex){
        throw new RuntimeException(ex);
      }
    }
    if(e.getSource() == ogretmenDersiBirakButonu && table.getSelectedRow() != -1){
      try {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT dersler FROM hocalar WHERE sicil_no = ?");
        preparedStatement.setInt(1, ogretmen.sicilNo);
        ResultSet resultSet = preparedStatement.executeQuery();

        PreparedStatement preparedStatement1 = connection.prepareStatement("UPDATE hocalar SET dersler = ? WHERE sicil_no = ?");
        preparedStatement1.setInt(2, ogretmen.sicilNo);

        if (resultSet.next()){
          String[] strings = (String[]) resultSet.getArray(1).getArray();

          ArrayList<String> stringList = new ArrayList<>(Arrays.asList(strings));
          stringList.remove(table.getValueAt(table.getSelectedRow(), 0));
          strings = stringList.toArray(new String[0]);
          Array sqlArray = connection.createArrayOf("text", strings);
          preparedStatement1.setArray(1, sqlArray);
          preparedStatement1.executeUpdate();

          preparedStatement1.close();
          preparedStatement.close();
          resultSet.close();

          table.setValueAt("Dersi Vermiyor", table.getSelectedRow(), 1);
          panel.repaint();
        }
      }
      catch (SQLException ex){
        throw new RuntimeException(ex);
      }
    }
    if(e.getSource() == ogretmenIlgiAlanlariListeleButonu){
      OgretmenIlgiAlanlariListele();
    }
    if(e.getSource() == ogretmenIlgiAlaniEkleButonu){
      try{
        if(table.getSelectedRow() == -1) return;

        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE hocalar SET ilgi_alanlari = ? WHERE sicil_no = ?");
        preparedStatement.setInt(2, ogretmen.sicilNo);

        PreparedStatement preparedStatement1 = connection.prepareStatement("SELECT ilgi_alanlari FROM hocalar WHERE sicil_no = ?");
        preparedStatement1.setInt(1, ogretmen.sicilNo);

        ResultSet resultSet1 = preparedStatement1.executeQuery();
        resultSet1.next();

        String[] dizi = (String[]) resultSet1.getArray(1).getArray();
        for (String eleman : dizi) {
          if (eleman.equals(table.getValueAt(table.getSelectedRow(), 0))) {
            return;
          }
        }

        String[] strings = (String[]) resultSet1.getArray(1).getArray();

        String[] strings1 = new String[strings.length + 1];
        System.arraycopy(strings, 0, strings1, 0, strings.length);
        strings1[strings1.length - 1] = (String) table.getValueAt(table.getSelectedRow(), 0);
        Array sqlArray = connection.createArrayOf("text", strings1);
        preparedStatement.setArray(1, sqlArray);
        preparedStatement.executeUpdate();

        table.setValueAt("Dahil", table.getSelectedRow(), 1);
      }
      catch (SQLException ex){
        throw new RuntimeException(ex);
      }
    }
    if(e.getSource() == ogretmenIlgiAlaniSilButonu && table.getSelectedRow() != -1){
      try {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT ilgi_alanlari FROM hocalar WHERE sicil_no = ?");
        preparedStatement.setInt(1, ogretmen.sicilNo);
        ResultSet resultSet = preparedStatement.executeQuery();

        PreparedStatement preparedStatement1 = connection.prepareStatement("UPDATE hocalar SET ilgi_alanlari = ? WHERE sicil_no = ?");
        preparedStatement1.setInt(2, ogretmen.sicilNo);

        if (resultSet.next()){
          String[] strings = (String[]) resultSet.getArray(1).getArray();

          ArrayList<String> stringList = new ArrayList<>(Arrays.asList(strings));
          stringList.remove(table.getValueAt(table.getSelectedRow(), 0));
          strings = stringList.toArray(new String[0]);
          Array sqlArray = connection.createArrayOf("text", strings);
          preparedStatement1.setArray(1, sqlArray);
          preparedStatement1.executeUpdate();

          preparedStatement1.close();
          preparedStatement.close();
          resultSet.close();

          table.setValueAt("Dahil Degil", table.getSelectedRow(), 1);
          panel.repaint();
        }
      }
      catch (SQLException ex){
        throw new RuntimeException(ex);
      }
    }
    if(e.getSource() == ogrenciIlgiAlanlariButonu){
      OgrenciIlgiAlaniGoruntule();
    }
    if(e.getSource() == ogrenciIlgiAlaniEkleButonu){
      try{
        if(table.getSelectedRow() == -1) return;

        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE ogrenciler SET ilgi_alanlari = ? WHERE ogrenci_no = ?");
        preparedStatement.setInt(2, ogrenci.no);

        PreparedStatement preparedStatement1 = connection.prepareStatement("SELECT ilgi_alanlari FROM ogrenciler WHERE ogrenci_no = ?");
        preparedStatement1.setInt(1, ogrenci.no);

        ResultSet resultSet1 = preparedStatement1.executeQuery();
        resultSet1.next();

        String[] dizi = (String[]) resultSet1.getArray(1).getArray();
        for (String eleman : dizi) {
          if (eleman.equals(table.getValueAt(table.getSelectedRow(), 0))) {
            return;
          }
        }

        String[] strings = (String[]) resultSet1.getArray(1).getArray();

        String[] strings1 = new String[strings.length + 1];
        System.arraycopy(strings, 0, strings1, 0, strings.length);
        strings1[strings1.length - 1] = (String) table.getValueAt(table.getSelectedRow(), 0);
        Array sqlArray = connection.createArrayOf("text", strings1);
        preparedStatement.setArray(1, sqlArray);
        preparedStatement.executeUpdate();

        table.setValueAt("Dahil", table.getSelectedRow(), 1);
      }
      catch (SQLException ex){
        throw new RuntimeException(ex);
      }
    }
    if(e.getSource() == ogrenciIlgiAlaniSilButonu && table.getSelectedRow() != -1){
      try {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT ilgi_alanlari FROM ogrenciler WHERE ogrenci_no = ?");
        preparedStatement.setInt(1, ogrenci.no);
        ResultSet resultSet = preparedStatement.executeQuery();

        PreparedStatement preparedStatement1 = connection.prepareStatement("UPDATE ogrenciler SET ilgi_alanlari = ? WHERE ogrenci_no = ?");
        preparedStatement1.setInt(2, ogrenci.no);

        if (resultSet.next()){
          String[] strings = (String[]) resultSet.getArray(1).getArray();

          ArrayList<String> stringList = new ArrayList<>(Arrays.asList(strings));
          stringList.remove(table.getValueAt(table.getSelectedRow(), 0));
          strings = stringList.toArray(new String[0]);
          Array sqlArray = connection.createArrayOf("text", strings);
          preparedStatement1.setArray(1, sqlArray);
          preparedStatement1.executeUpdate();

          preparedStatement1.close();
          preparedStatement.close();
          resultSet.close();

          table.setValueAt("Dahil Degil", table.getSelectedRow(), 1);
          panel.repaint();
        }
      }
      catch (SQLException ex){
        throw new RuntimeException(ex);
      }
    }
    if(e.getSource() == ogrenciDersleriGoruntuleButonu){
      OgrenciDersleriGoruntule();
    }
    if(e.getSource() == ogrenciTalepGonderButonu && table.getSelectedRow() != -1 && table.getValueAt(table.getSelectedRow(), 3).equals("Talep Yok")){
      try{
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO public.anlasmalar(" +
                "ogrenci_no, ogretmen_no, ders, gonderen, ogrenci_mesaj, ogretmen_mesaj, durum) " +
                "VALUES (?, ?, ?, 'ogrenci', '', '', 'beklemede')");
        preparedStatement.setInt(1, ogrenci.no);
        preparedStatement.setInt(2, (int)table.getValueAt(table.getSelectedRow(), 0));
        preparedStatement.setString(3, (String) table.getValueAt(table.getSelectedRow(), 2));
        preparedStatement.executeUpdate();
        table.setValueAt("Beklemede", table.getSelectedRow(), 3);
      }
      catch (SQLException ex){
        throw new RuntimeException(ex);
      }
    }
    if(e.getSource() == ogrenciTalepSilButonu && table.getSelectedRow() != -1 && (table.getValueAt(table.getSelectedRow(), 3).equals("Beklemede") || table.getValueAt(table.getSelectedRow(), 3).equals("Ret"))){
      try {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM anlasmalar WHERE ogretmen_no = ? AND ogrenci_no = ? AND ders = ?");
        preparedStatement.setInt(1, (int)table.getValueAt(table.getSelectedRow(), 0));
        preparedStatement.setInt(2, ogrenci.no);
        preparedStatement.setString(3, (String)table.getValueAt(table.getSelectedRow(), 2));
        preparedStatement.executeUpdate();
        table.setValueAt("Talep Yok", table.getSelectedRow(), 3);
      }
      catch (SQLException ex){
        throw new RuntimeException(ex);
      }
    }
    if(e.getSource() == ogrenciPDFYukleButonu){
      JFileChooser fileChooser = new JFileChooser();
      fileChooser.setFileFilter(new FileNameExtensionFilter("PDF Dosyaları", "pdf"));

      int result = fileChooser.showOpenDialog(null);

      if (result == JFileChooser.APPROVE_OPTION) {
        try {
          String selectedFilePath = fileChooser.getSelectedFile().getAbsolutePath();
          File file = new File(selectedFilePath);

          PDDocument pdfDDocument = Loader.loadPDF(file);
          PDFTextStripper pdfTextStripper = new PDFTextStripper();
          String docText = pdfTextStripper.getText(pdfDDocument);
          //System.out.println(docText);

          //String regexPattern = "([A-Z]{3}[0-9]{3})\s(.*?)\s\(.*?\)\nZ\sTr\s[0-9]\s[0-9]\s[0-9]\s[0-9]\s[0-9A-Z]{3}\s\s";
          String regexPattern = "[A-Z]{3}[0-9]{3}\\s.*\\r\\n|[\\r\\n]\\(.+\\)[\\r\\n]\\d+ Tr \\d{4} \\d{2}[A-Z]{2} [A-Z]";
          Pattern pattern = Pattern.compile(regexPattern);
          Matcher matcher = pattern.matcher(docText);

          List<String> matches = new ArrayList<>();

          while (matcher.find()) {
            String match = matcher.group();
            matches.add(match);
          }

          for (String match : matches) {
            System.out.println(match);
          }

          pdfDDocument.close();
        } catch (IOException ex) {
          throw new RuntimeException(ex);
        }
      } else {
        System.out.println("Dosya seçilmedi.");
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
