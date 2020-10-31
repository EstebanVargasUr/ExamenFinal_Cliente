package org.una.examenfinalcliente.WebService;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.una.examenfinalcliente.DTOs.CobroPendienteDTO;
import org.una.examenfinalcliente.DTOs.MembresiaDTO;
import org.una.examenfinalcliente.utility.JSONUtils;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CobroPendienteWebService {
    private static final HttpClient client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();
    private static final String serviceURL = "http://localhost:8095/cobrosPendientes";

    public static void getAllCobrosPendientes() throws InterruptedException, ExecutionException, JsonParseException, JsonMappingException, IOException
    {
        HttpRequest req = HttpRequest.newBuilder(URI.create(serviceURL+"/findAll")).GET().build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(req, HttpResponse.BodyHandlers.ofString());
        response.thenAccept(res -> System.out.println(res));
        List<CobroPendienteDTO> cobrosPendientes = JSONUtils.convertFromJsonToList(response.get().body(), new TypeReference<List<CobroPendienteDTO>>() {});
        cobrosPendientes.forEach(System.out::println);
        response.join();
    }

    public static void getCobrosPendientesById(long id) throws InterruptedException, ExecutionException, IOException
    {
        HttpRequest req = HttpRequest.newBuilder(URI.create(serviceURL+"/findById/"+id)).GET().build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(req, HttpResponse.BodyHandlers.ofString());
        response.thenAccept(res -> System.out.println(res));

        if(response.get().statusCode() == 500)
            System.out.println("Cobro Pendiente No Encontrado");

        else
        {
            CobroPendienteDTO bean = JSONUtils.covertFromJsonToObject(response.get().body(), CobroPendienteDTO.class);
            System.out.println(bean);
        }
        response.join();
    }

    public static List<CobroPendienteDTO> getCobrosPendientesByMembresiaId(long id) throws InterruptedException, ExecutionException, IOException
    {
        HttpRequest req = HttpRequest.newBuilder(URI.create(serviceURL+"/findByMembresiaId/"+id)).GET().build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(req, HttpResponse.BodyHandlers.ofString());
        response.thenAccept(res -> System.out.println(res));
        List<CobroPendienteDTO> cobrosPendientes = JSONUtils.convertFromJsonToList(response.get().body(), new TypeReference<List<CobroPendienteDTO>>() {});
        cobrosPendientes.forEach(System.out::println);
        response.join();
        return cobrosPendientes;
    }

    public static Double getCobroPendienteMonto(long id) throws InterruptedException, ExecutionException, IOException {
        double monto = 0;
        HttpRequest req = HttpRequest.newBuilder(URI.create(serviceURL + "/calcularMontoCobro/" + id)).GET().build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(req, HttpResponse.BodyHandlers.ofString());
        response.thenAccept(res -> System.out.println(res));

        if (response.get().statusCode() == 500)
            System.out.println("Valor no encontrado");

        else {
            if (response.get().body().isBlank())
                return monto;

            else {
                System.out.println(response.get().body());
                monto = Long.parseLong(response.get().body());
            }

        }
        response.join();
        return monto;
    }

    public static void createCobroPendiente(Integer periodo, Date fechaVencimiento, MembresiaDTO membresia) throws InterruptedException, ExecutionException, JsonProcessingException
    {
        CobroPendienteDTO bean = new CobroPendienteDTO();

        bean.setPeriodo(periodo);
        bean.setFechaVencimiento(fechaVencimiento);
        bean.setMembresia(membresia);

        String inputJson = JSONUtils.covertFromObjectToJson(bean);
        HttpRequest request = HttpRequest.newBuilder(URI.create(serviceURL+"/"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(inputJson)).build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(request,HttpResponse.BodyHandlers.ofString());
        System.out.println(response.get().body());

    }

    public static void updateCobroPendiente(CobroPendienteDTO bean, long id) throws InterruptedException, ExecutionException, IOException
    {
        String inputJson=JSONUtils.covertFromObjectToJson(bean);
        HttpRequest request = HttpRequest.newBuilder(URI.create(serviceURL+"/"+id))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(inputJson)).build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(request,HttpResponse.BodyHandlers.ofString());

        if(response.get().statusCode() == 500)
            System.out.println("No se pudo actualizar el cliente");

        else {
            bean = JSONUtils.covertFromJsonToObject(response.get().body(), CobroPendienteDTO .class);
            System.out.println(bean);
        }
        response.join();
    }
}
