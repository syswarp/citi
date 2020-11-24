/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package citi;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 *
 * @author riddick
 */
public class CITI {

    private static Properties PROPS;
    private static String SALIDA = "";
    private static String ENTIDAD = "";
    private static int ANIO;
    private static int MES;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, SQLException {

        PROPS = new Properties();
        PROPS.load(Conexion.class.getResourceAsStream("system.properties"));
        SALIDA = PROPS.getProperty("salida").trim();
        ENTIDAD = PROPS.getProperty("entidad").trim();

        ANIO = 2020;
        MES = 5;

        if (args.length == 2) {
            ANIO = Integer.parseInt(args[0]);
            MES = Integer.parseInt(args[1]);
        }

        // TODO code application logic here
        Conexion c = new Conexion();
        c.setConexionLocal();
        Connection conn = c.getConexionLocal();

// ventas        
        String linea = "";
        String archivo = SALIDA + "VENTAS" + ANIO + MES + ".TXT";
        ResultSet rsVentas = getVentas(ANIO, MES);
        while (rsVentas.next()) {
            linea += rsVentas.getString(1) + "\r";
        }
        crearArchivo(archivo, linea);
//ventas alicuotas
        linea = "";
        archivo = SALIDA + "VENTASALICUOTAS" + ANIO + MES + ".TXT";
        rsVentas = getVentasAlicuotas(ANIO, MES);
        while (rsVentas.next()) {
            linea += rsVentas.getString(1) + "\r";
        }
        crearArchivo(archivo, linea);

//compras 
        linea = "";
        archivo = SALIDA + "COMPRAS" + ANIO + MES + ".TXT";
        rsVentas = getCompras(ANIO, MES);
        while (rsVentas.next()) {
            linea += rsVentas.getString(1) + "\r";
        }
        crearArchivo(archivo, linea);

//compras alicuotas
        linea = "";
        archivo = SALIDA + "COMPRASALICUOTAS" + ANIO + MES + ".TXT";
        rsVentas = getComprasAlicuotas(ANIO, MES);
        while (rsVentas.next()) {
            linea += rsVentas.getString(1) + "\r";
        }
        crearArchivo(archivo, linea);

    }

    private static ResultSet getVentas(int ano, int mes) {
        ResultSet salida = null;
        try {
            Conexion c = new Conexion();
            c.setConexionLocal();
            Connection conn = c.getConexionLocal();
            Statement stmt = conn.createStatement();
            String SQL = "";
            SQL += "select output from reginfo_cv_ventas_cbte where date_part('year',fechamov) =" + ano + " and date_part('month',fechamov)=" + mes;

            ResultSet rs = stmt.executeQuery(SQL);
            salida = rs;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return salida;

    }

    private static ResultSet getVentasAlicuotas(int ano, int mes) {
        ResultSet salida = null;
        try {
            Conexion c = new Conexion();
            c.setConexionLocal();
            Connection conn = c.getConexionLocal();
            Statement stmt = conn.createStatement();
            String SQL = "";
            SQL += "select output from reginfo_cv_ventas_alicuotas where date_part('year',fechamov) =" + ano + " and date_part('month',fechamov)=" + mes;

            ResultSet rs = stmt.executeQuery(SQL);
            salida = rs;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return salida;

    }

    private static ResultSet getCompras(int ano, int mes) {
        ResultSet salida = null;
        try {
            Conexion c = new Conexion();
            c.setConexionLocal();
            Connection conn = c.getConexionLocal();
            Statement stmt = conn.createStatement();
            String SQL = "";
            SQL += "select salida from citi_compras_001 where date_part('year',fecha) =" + ano + " and date_part('month',fecha)=" + mes;

            ResultSet rs = stmt.executeQuery(SQL);
            salida = rs;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return salida;

    }

    private static ResultSet getComprasAlicuotas(int ano, int mes) {
        ResultSet salida = null;
        try {
            Conexion c = new Conexion();
            c.setConexionLocal();
            Connection conn = c.getConexionLocal();
            Statement stmt = conn.createStatement();
            String SQL = "";
            SQL += "select salida from citi_compras_002 where date_part('year',fecha) =" + ano + " and date_part('month',fecha)=" + mes;

            ResultSet rs = stmt.executeQuery(SQL);
            salida = rs;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return salida;

    }

    private static void crearArchivo(String filename, String datos) throws IOException {
        try {
            File file = new File(filename);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(datos);
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
