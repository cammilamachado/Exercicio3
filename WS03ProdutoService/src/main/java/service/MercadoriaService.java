package service;

import java.util.Scanner;
import java.time.LocalDate;
import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import dao.MercadoriaDAO;
import model.Mercadoria;
import spark.Request;
import spark.Response;

public class MercadoriaService {

    private MercadoriaDAO mercadoriaDAO = new MercadoriaDAO();
    private String form;
    private final int FORM_INSERT = 1;
    private final int FORM_DETAIL = 2;
    private final int FORM_UPDATE = 3;
    private final int FORM_ORDERBY_ID = 1;
    private final int FORM_ORDERBY_DESCRICAO = 2;
    private final int FORM_ORDERBY_VALOR = 3;

    public MercadoriaService() {
        makeForm();
    }

    public void makeForm() {
        makeForm(FORM_INSERT, new Mercadoria(), FORM_ORDERBY_DESCRICAO);
    }

    public void makeForm(int orderBy) {
        makeForm(FORM_INSERT, new Mercadoria(), orderBy);
    }

    public void makeForm(int tipo, Mercadoria mercadoria, int orderBy) {
        String nomeArquivo = "form.html";
        form = "";
        try {
            Scanner entrada = new Scanner(new File(nomeArquivo));
            while (entrada.hasNext()) {
                form += (entrada.nextLine() + "\n");
            }
            entrada.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        String umMercadoria = "";
        if (tipo != FORM_INSERT) {
            umMercadoria += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
            umMercadoria += "\t\t<tr>";
            umMercadoria += "\t\t\t<td align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;<a href=\"/Mercadoria/list/1\">Nova Mercadoria</a></b></font></td>";
            umMercadoria += "\t\t</tr>";
            umMercadoria += "\t</table>";
            umMercadoria += "\t<br>";
        }

        if (tipo == FORM_INSERT || tipo == FORM_UPDATE) {
            String action = "/Mercadoria/";
            String name, descricao, buttonLabel;
            if (tipo == FORM_INSERT) {
                action += "insert";
                name = "Inserir Mercadoria";
                descricao = "leite, pão, ...";
                buttonLabel = "Inserir";
            } else {
                action += "update/" + mercadoria.getID();
                name = "Atualizar Mercadoria (ID " + mercadoria.getID() + ")";
                descricao = Mercadoria.getDescricao();
                buttonLabel = "Atualizar";
            }
            umMercadoria += "\t<form class=\"form--register\" action=\"" + action
                    + "\" method=\"post\" id=\"form-add\">";
            umMercadoria += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
            umMercadoria += "\t\t<tr>";
            umMercadoria += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;" + name
                    + "</b></font></td>";
            umMercadoria += "\t\t</tr>";
            umMercadoria += "\t\t<tr>";
            umMercadoria += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
            umMercadoria += "\t\t</tr>";
            umMercadoria += "\t\t<tr>";
            umMercadoria += "\t\t\t<td>&nbsp;Descrição: <input class=\"input--register\" type=\"text\" name=\"descricao\" value=\""
                    + descricao + "\"></td>";
            umMercadoria += "\t\t\t<td>Valor: <input class=\"input--register\" type=\"text\" name=\"Valor\" value=\""
                    + mercadoria.getValor() + "\"></td>";
            umMercadoria += "\t\t\t<td>Quantidade: <input class=\"input--register\" type=\"text\" name=\"quantidade\" value=\""
                    + mercadoria.getQuantidade() + "\"></td>";
            umMercadoria += "\t\t</tr>";
            umMercadoria += "\t\t<tr>";
            umMercadoria += "\t\t\t<td>&nbsp;Data de produção: <input class=\"input--register\" type=\"text\" name=\"dataProducao\" value=\""
                    + mercadoria.getDataProducao().toString() + "\"></td>";
            umMercadoria += "\t\t\t<td>Validade: <input class=\"input--register\" type=\"text\" name=\"Validade\" value=\""
                    + mercadoria.getValidade().toString() + "\"></td>";
            umMercadoria += "\t\t\t<td align=\"center\"><input type=\"submit\" value=\"" + buttonLabel
                    + "\" class=\"input--main__style input--button\"></td>";
            umMercadoria += "\t\t</tr>";
            umMercadoria += "\t</table>";
            umMercadoria += "\t</form>";
        } else if (tipo == FORM_DETAIL) {
            umMercadoria += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
            umMercadoria += "\t\t<tr>";
            umMercadoria += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Detalhar Mercadoria (ID " + mercadoria.getID() + ")</b></font></td>";
            umMercadoria += "\t\t</tr>";
            umMercadoria += "\t\t<tr>";
            umMercadoria += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
            umMercadoria += "\t\t</tr>";
            umMercadoria += "\t\t<tr>";
            umMercadoria += "\t\t\t<td>&nbsp;Descrição: " + mercadoria.getDescricao() + "</td>";
            umMercadoria += "\t\t\t<td>Valor: " + mercadoria.getValor() + "</td>";
            umMercadoria += "\t\t\t<td>Quantidade: " + mercadoria.getQuantidade() + "</td>";
            umMercadoria += "\t\t</tr>";
            umMercadoria += "\t\t<tr>";
            umMercadoria += "\t\t\t<td>&nbsp;Data de produção: " + mercadoria.getDataProducao().toString() + "</td>";
            umMercadoria += "\t\t\t<td>Validade: " + mercadoria.getValidade().toString() + "</td>";
            umMercadoria += "\t\t\t<td>&nbsp;</td>";
            umMercadoria += "\t\t</tr>";
            umMercadoria += "\t</table>";
        } else {
            System.out.println("ERRO! Tipo não identificado " + tipo);
        }
        form = form.replaceFirst("<UM-MERCADORIA>", umMercadoria);

        String list = new String("<table width=\"80%\" align=\"center\" bgcolor=\"#f3f3f3\">");
        list += "\n<tr><td colspan=\"6\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Relação de Mercadorias</b></font></td></tr>\n"
                +
                "\n<tr><td colspan=\"6\">&nbsp;</td></tr>\n" +
                "\n<tr>\n" +
                "\t<td><a href=\"/mercadoria/list/" + FORM_ORDERBY_ID + "\"><b>ID</b></a></td>\n" +
                "\t<td><a href=\"/mercadoria/list/" + FORM_ORDERBY_DESCRICAO + "\"><b>Descrição</b></a></td>\n" +
                "\t<td><a href=\"/mercadoria/list/" + FORM_ORDERBY_VALOR + "\"><b>Valor</b></a></td>\n" +
                "\t<td width=\"100\" align=\"center\"><b>Detalhar</b></td>\n" +
                "\t<td width=\"100\" align=\"center\"><b>Atualizar</b></td>\n" +
                "\t<td width=\"100\" align=\"center\"><b>Excluir</b></td>\n" +
                "</tr>\n";

        List<Mercadoria> mercadorias;
        if (orderBy == FORM_ORDERBY_ID) {
            mercadorias = MercadoriaDAO.getOrderByID();
        } else if (orderBy == FORM_ORDERBY_DESCRICAO) {
            mercadorias = MercadoriaDAO.getOrderByDescricao();
        } else if (orderBy == FORM_ORDERBY_VALOR) {
            mercadorias = MercadoriaDAO.getOrderByValor();
        } else {
            mercadorias = MercadoriaDAO.get();
        }

        int i = 0;
        String bgcolor = "";
        for (Mercadoria p : mercadorias) {
            bgcolor = (i++ % 2 == 0) ? "#fff5dd" : "#dddddd";
            list += "\n<tr bgcolor=\"" + bgcolor + "\">\n" +
                    "\t<td>" + p.getID() + "</td>\n" +
                    "\t<td>" + p.getDescricao() + "</td>\n" +
                    "\t<td>" + p.getValor() + "</td>\n" +
                    "\t<td align=\"center\" valign=\"middle\"><a href=\"/mercadoria/" + p.getID()
                    + "\"><img src=\"/image/detail.png\" width=\"20\" height=\"20\"/></a></td>\n" +
                    "\t<td align=\"center\" valign=\"middle\"><a href=\"/mercadoria/update/" + p.getID()
                    + "\"><img src=\"/image/update.png\" width=\"20\" height=\"20\"/></a></td>\n" +
                    "\t<td align=\"center\" valign=\"middle\"><a href=\"javascript:confirmarDeleteMercadoria('"
                    + p.getID() + "', '" + p.getDescricao() + "', '" + p.getValor()
                    + "');\"><img src=\"/image/delete.png\" width=\"20\" height=\"20\"/></a></td>\n" +
                    "</tr>\n";
        }
        list += "</table>";
        form = form.replaceFirst("<LISTAR-MERCADORIA>", list);
    }

    public Object insert(Request request, Response response) {
        String descricao = request.queryParams("descricao");
        float valor = Float.parseFloat(request.queryParams("valor"));
        int quantidade = Integer.parseInt(request.queryParams("quantidade"));
        LocalDateTime dataProducao = LocalDateTime.parse(request.queryParams("dataProducao"));
        LocalDate Validade = LocalDate.parse(request.queryParams("Validade"));

        String resp = "";

        Mercadoria mercadoria = new Mercadoria(-1, descricao, valor, quantidade, dataProducao, Validade);

        if (MercadoriaDAO.insert(mercadoria) == true) {
            resp = "Mercadoria (" + descricao + ") inserida!";
            response.status(201); // 201 Created
        } else {
            resp = "Mercadoria(" + descricao + ") não inserida!";
            response.status(404); // 404 Not found
        }

        makeForm();
        return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">",
                "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"" + resp + "\">");
    }

    public Object get(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        Mercadoria mercadoria = (Mercadoria) MercadoriaDAO.get(id);

        if (mercadoria != null) {
            response.status(200); // success
            makeForm(FORM_DETAIL, mercadoria, FORM_ORDERBY_DESCRICAO);
        } else {
            response.status(404); // 404 Not found
            String resp = "Mercadoria " + id + " não encontrada.";
            makeForm();
            form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">",
                    "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"" + resp + "\">");
        }

        return form;
    }

    public Object getToUpdate(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        Mercadoria mercadoria = (Mercadoria) MercadoriaDAO.get(id);

        if (mercadoria != null) {
            response.status(200); // success
            makeForm(FORM_UPDATE, mercadoria, FORM_ORDERBY_DESCRICAO);
        } else {
            response.status(404); // 404 Not found
            String resp = "Mercadoria " + id + " não encontrada.";
            makeForm();
            form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">",
                    "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"" + resp + "\">");
        }

        return form;
    }

    public Object getAll(Request request, Response response) {
        int orderBy = Integer.parseInt(request.params(":orderby"));
        makeForm(orderBy);
        response.header("Content-Type", "text/html");
        response.header("Content-Encoding", "UTF-8");
        return form;
    }

    public Object update(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        Mercadoria mercadoria = MercadoriaDAO.get(id);
        String resp = "";

        if (mercadoria != null) {
            mercadoria.setDescricao(request.queryParams("descricao"));
            mercadoria.setValor(Float.parseFloat(request.queryParams("valor")));
            mercadoria.setQuantidade(Integer.parseInt(request.queryParams("quantidade")));
            mercadoria.setDataProducao(LocalDateTime.parse(request.queryParams("dataProducao")));
            mercadoria.setValidade(LocalDate.parse(request.queryParams("Validade")));
            MercadoriaDAO.update(mercadoria);
            response.status(200); // success
            resp = "Mercadoria (ID " + mercadoria.getID() + ") atualizado!";
        } else {
            response.status(404); // 404 Not found
            resp = "Mercadoria (ID \" + mercadoria.getId() + \") não encontrado!";
        }
        makeForm();
        return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">",
                "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"" + resp + "\">");
    }

    public Object delete(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        Mercadoria mercadoria = MercadoriaDAO.get(id);
        String resp = "";

        if (mercadoria != null) {
            MercadoriaDAO.delete(id);
            response.status(200); // success
            resp = "Mercadoria (" + id + ") deletada!";
        } else {
            response.status(404); // 404 Not found
            resp = "Mercadoria (" + id + ") não encontrada!";
        }
        makeForm();
        return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">",
                "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"" + resp + "\">");
    }
}
