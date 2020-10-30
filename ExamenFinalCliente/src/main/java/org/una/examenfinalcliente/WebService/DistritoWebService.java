package org.una.examenfinalcliente.WebService;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.una.examenfinalcliente.utility.JSONUtils;
import org.una.examenfinalcliente.DTOs.DistritoDTO;
/**
 *
 * @author Adrian
 */
public class DistritoWebService {
    
    private static final HttpClient client = HttpClient.newBuilder().version(Version.HTTP_2).build();
    private static final String serviceURL = "http://localhost:8099/exa_adr_distritos";
    
    public static void getAllDistritos() throws InterruptedException, ExecutionException, JsonParseException, JsonMappingException, IOException
    {
        HttpRequest req = HttpRequest.newBuilder(URI.create(serviceURL+"/findAll")).GET().build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(req, BodyHandlers.ofString());
        response.thenAccept(res -> System.out.println(res));
        List<DistritoDTO> distritos = JSONUtils.convertFromJsonToList(response.get().body(), new TypeReference<List<DistritoDTO>>() {});
        distritos.forEach(System.out::println);
        response.join();
    }

    public static void getDistritoById(long id) throws InterruptedException, ExecutionException, IOException
    {
        HttpRequest req = HttpRequest.newBuilder(URI.create(serviceURL+"/findById/"+id)).GET().build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(req, BodyHandlers.ofString());
        response.thenAccept(res -> System.out.println(res));

        if(response.get().statusCode() == 500)
            System.out.println("Distrito No Encontrado");

        else
        {
            DistritoDTO bean = JSONUtils.covertFromJsonToObject(response.get().body(), DistritoDTO.class);
            System.out.println(bean);
        }
        response.join();
    }
    
    public static void getDistritoByNombreDistrito(String nombre) throws InterruptedException, ExecutionException, IOException
    {
        HttpRequest req = HttpRequest.newBuilder(URI.create(serviceURL+"/findByNombreDistritoAproximateIgnoreCase/"+nombre)).GET().build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(req, BodyHandlers.ofString());
        response.thenAccept(res -> System.out.println(res));
        List<DistritoDTO> distritos = JSONUtils.convertFromJsonToList(response.get().body(), new TypeReference<List<DistritoDTO>>() {});
        distritos.forEach(System.out::println);
        response.join();
    }
    
    public static void getDistritoByCodigoDistrito(Integer codigo) throws InterruptedException, ExecutionException, IOException
    {
        HttpRequest req = HttpRequest.newBuilder(URI.create(serviceURL+"/findByCodigoDistrito/"+codigo)).GET().build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(req, BodyHandlers.ofString());
        response.thenAccept(res -> System.out.println(res));

        if(response.get().statusCode() == 500)
            System.out.println("Distrito No Encontrado");

        else
        {
            DistritoDTO bean = JSONUtils.covertFromJsonToObject(response.get().body(), DistritoDTO.class);
            System.out.println(bean);
        }
        response.join();
    }

    public static String getSumaCantidadPoblacionByDistritoId(long idDistrito) throws InterruptedException, ExecutionException, IOException
    {
        HttpRequest req = HttpRequest.newBuilder(URI.create(serviceURL+"/SumaCantidadPoblacionByDistritoId/"+idDistrito)).GET().build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(req, BodyHandlers.ofString());
        response.thenAccept(res -> System.out.println(res));

        if(response.get().statusCode() == 500)
            System.out.println("Distrito No Encontrado");

        else
        {
            System.out.println(response.get().body());
        }
        response.join();
        return response.get().body();
    }
    
    public static String getSumaAreaCuadradaByDistritoId(long idDistrito) throws InterruptedException, ExecutionException, IOException
    {
        HttpRequest req = HttpRequest.newBuilder(URI.create(serviceURL+"/SumaAreaCuadradaByDistritoId/"+idDistrito)).GET().build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(req, BodyHandlers.ofString());
        response.thenAccept(res -> System.out.println(res));

        if(response.get().statusCode() == 500)
            System.out.println("Distrito No Encontrado");

        else
        {
            System.out.println(response.get().body());
        }
        
        response.join();
        return response.get().body();
    }

    public static void createDistrito(String nombreDistrito, Integer codigoDistrito) throws InterruptedException, ExecutionException, JsonProcessingException
    {
        DistritoDTO bean = new DistritoDTO();
        
        bean.setNombreDistrito(nombreDistrito);
        bean.setCodigoDistrito(codigoDistrito);

        String inputJson = JSONUtils.covertFromObjectToJson(bean);
        HttpRequest request = HttpRequest.newBuilder(URI.create(serviceURL+"/"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(inputJson)).build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(request,HttpResponse.BodyHandlers.ofString());
        System.out.println(response.get().body());

    }

    public static void updateDistrito(DistritoDTO bean, long id) throws InterruptedException, ExecutionException, IOException
    {
        String inputJson=JSONUtils.covertFromObjectToJson(bean);
        HttpRequest request = HttpRequest.newBuilder(URI.create(serviceURL+"/"+id))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(inputJson)).build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(request,HttpResponse.BodyHandlers.ofString());

        if(response.get().statusCode() == 500)
            System.out.println("No se pudo actualizar el Distrito");

        else {
            bean = JSONUtils.covertFromJsonToObject(response.get().body(), DistritoDTO.class);
            System.out.println(bean);
        }
        response.join();
    }

    public static void deleteDistrito(long id) throws ExecutionException, InterruptedException, IOException
    {   
        HttpRequest req = HttpRequest.newBuilder(URI.create(serviceURL+"/deleteById/"+id)).DELETE().build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(req, BodyHandlers.ofString());
        response.thenAccept(res -> System.out.println(res));

        if(response.get().statusCode() == 500)
            System.out.println("Distrito No Encontrado");

        else
        {
            System.out.println("Distrito eliminado");
        }
    } 
}
