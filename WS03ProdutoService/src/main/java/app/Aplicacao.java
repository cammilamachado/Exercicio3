package app;

import static spark.Spark.*;
import service.MercadoriaService;

public class Aplicacao {

    private static MercadoriaService mercadoriaService = new MercadoriaService();

    public static void main(String[] args) {
        port(6789);

        staticFiles.location("/public");

        post("/mercadoria/insert", (request, response) -> mercadoriaService.insert(request, response));

        get("/mercadoria/:id", (request, response) -> mercadoriaService.get(request, response));

        get("/mercadoria/list/:orderby", (request, response) -> mercadoriaService.getAll(request, response));

        get("/mercadoria/update/:id", (request, response) -> mercadoriaService.getToUpdate(request, response));

        post("/mercadoria/update/:id", (request, response) -> mercadoriaService.update(request, response));

        get("mercadoria/delete/:id", (request, response) -> mercadoriaService.delete(request, response));

    }
}
