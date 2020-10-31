package org.una.examenfinalcliente.WebService;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.una.examenfinalcliente.DTOs.ClienteDTO;
import org.una.examenfinalcliente.utility.JSONUtils;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class ClienteWebService {

    private static final HttpClient client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();
    private static final String serviceURL = "http://localhost:8095/clientes";

    public static List<ClienteDTO> getAllClientes() throws InterruptedException, ExecutionException, JsonParseException, JsonMappingException, IOException
    {
        HttpRequest req = HttpRequest.newBuilder(URI.create(serviceURL+"/findAll")).GET().build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(req, HttpResponse.BodyHandlers.ofString());
        response.thenAccept(res -> System.out.println(res));
        List<ClienteDTO> clientes = JSONUtils.convertFromJsonToList(response.get().body(), new TypeReference<List<ClienteDTO>>() {});
        clientes.forEach(System.out::println);
        response.join();
        return clientes;
    }

    public static void getClienteById(long id) throws InterruptedException, ExecutionException, IOException
    {
        HttpRequest req = HttpRequest.newBuilder(URI.create(serviceURL+"/findById/"+id)).GET().build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(req, HttpResponse.BodyHandlers.ofString());
        response.thenAccept(res -> System.out.println(res));

        if(response.get().statusCode() == 500)
            System.out.println("Cliente No Encontrado");

        else
        {
            ClienteDTO bean = JSONUtils.covertFromJsonToObject(response.get().body(), ClienteDTO.class);
            System.out.println(bean);
        }
        response.join();
    }

    public static void createCliente(String nombreCompleto, String cedula, String telefono) throws InterruptedException, ExecutionException, JsonProcessingException
    {
        ClienteDTO bean = new ClienteDTO();

        bean.setNombreCompleto(nombreCompleto);
        bean.setCedula(cedula);
        bean.setTelefono(telefono);

        String inputJson = JSONUtils.covertFromObjectToJson(bean);
        HttpRequest request = HttpRequest.newBuilder(URI.create(serviceURL+"/"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(inputJson)).build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(request,HttpResponse.BodyHandlers.ofString());
        System.out.println(response.get().body());

    }

    public static void updateCliente(ClienteDTO bean, long id) throws InterruptedException, ExecutionException, IOException
    {
        String inputJson=JSONUtils.covertFromObjectToJson(bean);
        HttpRequest request = HttpRequest.newBuilder(URI.create(serviceURL+"/"+id))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(inputJson)).build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(request,HttpResponse.BodyHandlers.ofString());

        if(response.get().statusCode() == 500)
            System.out.println("No se pudo actualizar el cliente");

        else {
            bean = JSONUtils.covertFromJsonToObject(response.get().body(), ClienteDTO.class);
            System.out.println(bean);
        }
        response.join();
    }
}
