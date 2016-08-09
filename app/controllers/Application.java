package controllers;

import play.data.Form;
import play.mvc.*;
import models.*;
import play.db.jpa.*;
import views.html.*;
import play.data.FormFactory;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;


public class Application extends Controller {

    @Inject
    private FormFactory formFactory;
    private List<Usuario> listaDeUsuarios = new ArrayList<>();
    private List<ArquivoTxt> listaDeArquivos = new ArrayList<>();
    private Usuario usuarioLogado = null;


    public Result cadastrarUsuario(){
        Usuario usuario = formFactory.form(Usuario.class).bindFromRequest().get();
        listaDeUsuarios.add(usuario);

        if (validarLogin(usuario.getEmail(), usuario.getSenha())){
            return redirect(routes.Application.chamarHome());
        }
        return redirect(routes.Application.index());
    }

    public Result escreverTexto(){
        Usuario usuario = formFactory.form(Usuario.class).bindFromRequest().get();
//        listaDeUsuarios.add(usuario);

        if (validarLogin(usuario.getEmail(), usuario.getSenha())){
            return redirect(routes.Application.chamarHome());
        }
        return redirect(routes.Application.index());
    }
    public Result logar(){
        Usuario usuario = formFactory.form(Usuario.class).bindFromRequest().get();

        if (validarLogin(usuario.getEmail(), usuario.getSenha())){
            return redirect(routes.Application.chamarHome());
        }

        return redirect(routes.Application.index());
    }

    public Result logOut(){
        usuarioLogado = null;
        return redirect(routes.Application.index());
    }

    //Validacao
    public boolean validarLogin(String email, String senha){

        for (int i = 0; i < listaDeUsuarios.size(); i++){
            Usuario usuario = listaDeUsuarios.get(i);
            if (usuario.getEmail().equals(email)){
                if (usuario.getSenha().equals(senha)){
                    usuarioLogado = usuario;
                    return true;
                }else{
                    return false;
                }
            }
        }
        return false;
    }

    //Renders
    public Result index() {
        return ok(index.render());
    }

    public Result chamarLogin() {
        return ok(login.render(listaDeUsuarios));
    }

    public Result chamarCadastro() {
        return ok(cadastro.render(listaDeUsuarios));
    }

    public Result menuUsuario(){
        return ok(usuario.render(usuarioLogado));

    }
    public Result chamarHome() {
        return ok(home.render(usuarioLogado));
    }

    public Result chamaTexto(){return ok(texto.render(listaDeArquivos));}


    /*public  Result salvaArquivo(){

        Arquivo arquivo = formFactory.form(ArquivoTxt.class).bindFromRequest().get();
        listaDeArquivos.add(arquivo);

        return redirect(routes.HomeController.chamarHome());}
*/

    public Result criaPasta(){
        Diretorio dir = formFactory.form(Diretorio.class).bindFromRequest().get();

        if (dir.getNome() == null){
            return ok(home.render(usuarioLogado));
        }
        usuarioLogado.criaSubDiretorio(dir.getNome());
        return ok(home.render(usuarioLogado));
    }

    public Result criaArquivos(){
        ArquivoTxt arquivo = formFactory.form(ArquivoTxt.class).bindFromRequest().get();
        listaDeArquivos.add(arquivo);
        usuarioLogado.addArquivo(arquivo.getNomeArquivo(), arquivo.getconteudoFile());
        return ok(home.render(usuarioLogado));
    }
    //GETs and SETs
    public List<Usuario> getListaDeUsuarios() {
        return listaDeUsuarios;
    }
    public List<ArquivoTxt> getListaDeArquivos() {
        return listaDeArquivos;
    }
}
