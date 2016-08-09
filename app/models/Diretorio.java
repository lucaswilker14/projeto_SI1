package models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marco on 02/08/2016.
 */
public class Diretorio {
    public String nome;
    public List<Diretorio> subDiretorios;
    public List<ArquivoTxt> arquivos;

    public Diretorio(String nome){
        this.nome = nome;
        this.subDiretorios = new ArrayList<>();
        this.arquivos = new ArrayList<>();
    }

    public Diretorio(){
        this.subDiretorios = new ArrayList<>();
        this.arquivos = new ArrayList<>();
    }

    public String getNome(){
        return this.nome;
    }

    public void adicionaDiretorio(Diretorio diretorio){
        subDiretorios.add(diretorio);
    }

    public List<Diretorio> getSubDiretorios(){
        return this.subDiretorios;
    }

    public List<ArquivoTxt> getArquivos() {
        return arquivos;
    }
}
