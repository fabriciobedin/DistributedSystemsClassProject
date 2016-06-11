/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.upf.sd.soapclient;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Victor
 */
public class CarroTableModel extends AbstractTableModel {

    private ArrayList carros;
    private String[] colunas;

    public CarroTableModel(ArrayList carros, String[] col) {
        setCarros(carros);
        setColunas(col);
    }

    /**
     * @return the carros
     */
    public ArrayList getCarros() {
        return carros;
    }

    /**
     * @param carros the carros to set
     */
    public void setCarros(ArrayList carros) {
        this.carros = carros;
    }

    /**
     * @return the colunas
     */
    public String[] getColunas() {
        return colunas;
    }

    /**
     * @param colunas the colunas to set
     */
    public void setColunas(String[] colunas) {
        this.colunas = colunas;
    }
    /**
     * @return 
     */
    public int getColumnCount () {
        return colunas.length;
    }
    
    public int getRowCount () {
        return carros.size();
    }
    
    public String getColumnName (int numCol){
        return colunas[numCol];
    }
    public Object getValueAt(int numLin, int numCol) {
        
        Object[] carro;
        carro = (Object[])getCarros().get(numLin);
        return carro[numCol];
    }
}
