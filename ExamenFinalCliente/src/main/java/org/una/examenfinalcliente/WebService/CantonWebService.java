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
import org.una.examenfinalcliente.DTOs.CantonDTO;
import org.una.examenfinalcliente.DTOs.DistritoDTO;
/**
 *
 * @author Adrian
 */
public class CantonWebService {
    
    private static final HttpClient client = HttpClient.newBuilder().version(Version.HTTP_2).build();
    private static final String serviceURL = "http://localhost:8099/exa_adr_cantones";
    
    public static void getAllCantones() throws InterruptedException, ExecutionException, JsonParseException, JsonMappingException, IOException
    {
        HttpRequest req = HttpRequest.newBuilder(URI.create(serviceURL+"/findAll")).GET().build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(req, BodyHandlers.ofString());
        response.thenAccept(res -> System.out.println(res));
        List<CantonDTO> cantones = JSONUtils.convertFromJsonToList(response.get().body(), new TypeReference<List<CantonDTO>>() {});
        cantones.forEach(System.out::println);
        response.join();
    }

    public static CantonDTO getCantonById(long id) throws InterruptedException, ExecutionException, IOException
    {
        CantonDTO bean = null;
        HttpRequest req = HttpRequest.newBuilder(URI.create(serviceURL+"/findById/"+id)).GET().build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(req, BodyHandlers.ofString());
        response.thenAccept(res -> System.out.println(res));

        if(response.get().statusCode() == 500)
            System.out.println("Canton No Encontrado");

        else
        {
            bean = JSONUtils.covertFromJsonToObject(response.get().body(), CantonDTO.class);
            System.out.println(bean);
        }
        response.join();
        return bean;
    }
    
    public static List<CantonDTO> getCantonByNombreCanton(String nombre) throws InterruptedException, ExecutionException, IOException
    {
        HttpRequest req = HttpRequest.newBuilder(URI.create(serviceURL+"/findByNombreCantonAproximateIgnoreCase/"+nombre)).GET().build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(req, BodyHandlers.ofString());
        response.thenAccept(res -> System.out.println(res));
        List<CantonDTO> cantones = JSONUtils.convertFromJsonToList(response.get().body(), new TypeReference<List<CantonDTO>>() {});
        cantones.forEach(System.out::println);
        response.join();
        return cantones;
    }
    
    public static CantonDTO getCantonByCodigoCanton(Integer codigo) throws InterruptedException, ExecutionException, IOException
    {
        CantonDTO bean = null;
        HttpRequest req = HttpRequest.newBuilder(URI.create(serviceURL+"/findByCodigoCanton/"+codigo)).GET().build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(req, BodyHandlers.ofString());
        response.thenAccept(res -> System.out.println(res));

        if(response.get().statusCode() == 500)
            System.out.println("Canton No Encontrado");

        else
        {
             bean = JSONUtils.covertFromJsonToObject(response.get().body(), CantonDTO.class);
            System.out.println(bean);
            
        }
        response.join();
        return bean;
    }

    public static Long getSumaCantidadPoblacionByCantonId(long idCanton) throws InterruptedException, ExecutionException, IOException
    {
        long total = 0;
        HttpRequest req = HttpRequest.newBuilder(URI.create(serviceURL+"/SumaCantidadPoblacionByCantonId/"+idCanton)).GET().build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(req, BodyHandlers.ofString());
        response.thenAccept(res -> System.out.println(res));

        if(response.get().statusCode() == 500)
            System.out.println("Canton No Encontrado");

        else
        {
           if (response.get().body().isBlank()) 
            return total;
            
            else
            {
                System.out.println(response.get().body());
                total = Long.parseLong(response.get().body()); 
            }  
        }
        response.join();
        return total;
    }
    
    public static Double getSumaAreaCuadradaByCantonId(long idCanton) throws InterruptedException, ExecutionException, IOException
    {
        double total = 0;
        HttpRequest req = HttpRequest.newBuilder(URI.create(serviceURL+"/SumaAreaCuadradaByCantonId/"+idCanton)).GET().build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(req, BodyHandlers.ofString());
        response.thenAccept(res -> System.out.println(res));

        if(response.get().statusCode() == 500)
            System.out.println("Canton No Encontrado");

        else
        {
             if (response.get().body().isBlank()) 
             return total;
            
            else
            {
                System.out.println(response.get().body());
                total = Double.parseDouble(response.get().body()); 
            }  
        }
        
        response.join();
        
        return total;
    }
    
    public static List<DistritoDTO> getDistritoByCantonId(long id) throws InterruptedException, ExecutionException, JsonParseException, JsonMappingException, IOException
    {
        HttpRequest req = HttpRequest.newBuilder(URI.create(serviceURL+"/findDistritoById/"+id)).GET().build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(req, BodyHandlers.ofString());
        response.thenAccept(res -> System.out.println(res));
        List<DistritoDTO> distritos = JSONUtils.convertFromJsonToList(response.get().body(), new TypeReference<List<DistritoDTO>>() {});
        distritos.forEach(System.out::println);
        response.join();
        return distritos;
    }

    public static void createCanton(String nombreCanton, Integer codigoCanton, long id) throws InterruptedException, ExecutionException, JsonProcessingException, IOException
    {
        CantonDTO bean = new CantonDTO();
        
        bean.setNombreCanton(nombreCanton);
        bean.setCodigoCanton(codigoCanton);
        
        bean.setProvincia(ProvinciaWebService.getProvinciaById(id));

        String inputJson = JSONUtils.covertFromObjectToJson(bean);
        HttpRequest request = HttpRequest.newBuilder(URI.create(serviceURL+"/"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(inputJson)).build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(request,HttpResponse.BodyHandlers.ofString());
        System.out.println(response.get().body());

    }

    public static void updateCanton(CantonDTO bean, long id) throws InterruptedException, ExecutionException, IOException
    {
        String inputJson=JSONUtils.covertFromObjectToJson(bean);
        HttpRequest request = HttpRequest.newBuilder(URI.create(serviceURL+"/"+id))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(inputJson)).build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(request,HttpResponse.BodyHandlers.ofString());

        if(response.get().statusCode() == 500)
            System.out.println("No se pudo actualizar el Canton");

        else {
            bean = JSONUtils.covertFromJsonToObject(response.get().body(), CantonDTO.class);
            System.out.println(bean);
        }
        response.join();
    }
    
    public static void deleteCanton(long id) throws ExecutionException, InterruptedException, IOException
    {   
        HttpRequest req = HttpRequest.newBuilder(URI.create(serviceURL+"/deleteById/"+id)).DELETE().build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(req, BodyHandlers.ofString());
        response.thenAccept(res -> System.out.println(res));

        if(response.get().statusCode() == 500)
            System.out.println("Canton No Encontrado");

        else
        {
            System.out.println("Canton eliminado");
        }
    } 
}