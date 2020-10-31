package org.una.examenfinalcliente.WebService;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.una.examenfinalcliente.DTOs.ClienteDTO;
import org.una.examenfinalcliente.DTOs.MembresiaDTO;
import org.una.examenfinalcliente.utility.JSONUtils;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class MembresiaWebService {

    private static final HttpClient client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();
    private static final String serviceURL = "http://localhost:8095/membresias";

    public static List<MembresiaDTO> getAllMembresias() throws InterruptedException, ExecutionException, JsonParseException, JsonMappingException, IOException
    {
        HttpRequest req = HttpRequest.newBuilder(URI.create(serviceURL+"/findAll")).GET().build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(req, HttpResponse.BodyHandlers.ofString());
        response.thenAccept(res -> System.out.println(res));
        List<MembresiaDTO> membresias = JSONUtils.convertFromJsonToList(response.get().body(), new TypeReference<List<MembresiaDTO>>() {});
        membresias.forEach(System.out::println);
        response.join();
        return membresias;
    }

    public static void getMembresiaById(long id) throws InterruptedException, ExecutionException, IOException
    {
        HttpRequest req = HttpRequest.newBuilder(URI.create(serviceURL+"/findById/"+id)).GET().build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(req, HttpResponse.BodyHandlers.ofString());
        response.thenAccept(res -> System.out.println(res));

        if(response.get().statusCode() == 500)
            System.out.println("Membresia No Encontrada");

        else
        {
            MembresiaDTO bean = JSONUtils.covertFromJsonToObject(response.get().body(), MembresiaDTO.class);
            System.out.println(bean);
        }
        response.join();
    }

    public static List<MembresiaDTO> getMembresiasByClienteId(long id) throws InterruptedException, ExecutionException, JsonParseException, JsonMappingException, IOException
    {
        HttpRequest req = HttpRequest.newBuilder(URI.create(serviceURL+"/findMembresiasByClienteId/"+id)).GET().build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(req, HttpResponse.BodyHandlers.ofString());
        response.thenAccept(res -> System.out.println(res));
        List<MembresiaDTO> membresias = JSONUtils.convertFromJsonToList(response.get().body(), new TypeReference<List<MembresiaDTO>>() {});
        membresias.forEach(System.out::println);
        response.join();
        return membresias;
    }

    public static void createMembresia(Integer periodicidad, Float monto, String descripcion) throws InterruptedException, ExecutionException, JsonProcessingException
    {
        MembresiaDTO bean = new MembresiaDTO();

        bean.setPeriodicidad(periodicidad);
        bean.setMonto(monto);
        bean.setDescripcion(descripcion);

        String inputJson = JSONUtils.covertFromObjectToJson(bean);
        HttpRequest request = HttpRequest.newBuilder(URI.create(serviceURL+"/"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(inputJson)).build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(request,HttpResponse.BodyHandlers.ofString());
        System.out.println(response.get().body());

    }

    public static void updateMembresia(MembresiaDTO bean, long id) throws InterruptedException, ExecutionException, IOException
    {
        String inputJson=JSONUtils.covertFromObjectToJson(bean);
        HttpRequest request = HttpRequest.newBuilder(URI.create(serviceURL+"/"+id))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(inputJson)).build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(request,HttpResponse.BodyHandlers.ofString());

        if(response.get().statusCode() == 500)
            System.out.println("No se pudo actualizar el cliente");

        else {
            bean = JSONUtils.covertFromJsonToObject(response.get().body(), MembresiaDTO.class);
            System.out.println(bean);
        }
        response.join();
    }
}
