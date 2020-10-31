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
import org.una.examenfinalcliente.DTOs.CantonDTO;
import org.una.examenfinalcliente.utility.JSONUtils;
import org.una.examenfinalcliente.DTOs.ProvinciaDTO;
/**
 *
 * @author Adrian
 */
public class ProvinciaWebService {
    
    private static final HttpClient client = HttpClient.newBuilder().version(Version.HTTP_2).build();
    private static final String serviceURL = "http://localhost:8099/exa_adr_provincias";
    
    public static List<ProvinciaDTO> getAllProvincias() throws InterruptedException, ExecutionException, JsonParseException, JsonMappingException, IOException
    {
        HttpRequest req = HttpRequest.newBuilder(URI.create(serviceURL+"/findAll")).GET().build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(req, BodyHandlers.ofString());
        response.thenAccept(res -> System.out.println(res));
        List<ProvinciaDTO> provincias = JSONUtils.convertFromJsonToList(response.get().body(), new TypeReference<List<ProvinciaDTO>>() {});
        provincias.forEach(System.out::println);
        response.join();
        return provincias;
    }

    public static ProvinciaDTO getProvinciaById(long id) throws InterruptedException, ExecutionException, IOException
    {
        ProvinciaDTO bean = null ;
        HttpRequest req = HttpRequest.newBuilder(URI.create(serviceURL+"/findById/"+id)).GET().build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(req, BodyHandlers.ofString());
        response.thenAccept(res -> System.out.println(res));

        if(response.get().statusCode() == 500)
            System.out.println("Provincia No Encontrada");

        else
        {
            if (response.get().body().isBlank()) {
                return null;
            }
            bean = JSONUtils.covertFromJsonToObject(response.get().body(), ProvinciaDTO.class);
            System.out.println(bean);
        }
        response.join();
        return bean;
    }
    
    public static List<ProvinciaDTO> getProvinciaByNombreProvincia(String nombre) throws InterruptedException, ExecutionException, IOException
    {
        HttpRequest req = HttpRequest.newBuilder(URI.create(serviceURL+"/findByNombreProvinciaAproximateIgnoreCase/"+nombre)).GET().build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(req, BodyHandlers.ofString());
        response.thenAccept(res -> System.out.println(res));
        List<ProvinciaDTO> provincias = JSONUtils.convertFromJsonToList(response.get().body(), new TypeReference<List<ProvinciaDTO>>() {});
        provincias.forEach(System.out::println);
        response.join();
        return provincias;
    }
    
    public static ProvinciaDTO getProvinciaByCodigoProvincia(Integer codigo) throws InterruptedException, ExecutionException, IOException
    {
        ProvinciaDTO bean = null ;
        HttpRequest req = HttpRequest.newBuilder(URI.create(serviceURL+"/findByCodigoProvincia/"+codigo)).GET().build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(req, BodyHandlers.ofString());
        response.thenAccept(res -> System.out.println(res));

        if(response.get().statusCode() == 500)
            System.out.println("Provincia No Encontrada");

        else
        {
            bean = JSONUtils.covertFromJsonToObject(response.get().body(), ProvinciaDTO.class);
            System.out.println(bean);
        }
        response.join();
        return bean;
    }

    public static Long getSumaCantidadPoblacionByProvinciaId(long idProvincia) throws InterruptedException, ExecutionException, IOException
    {
        long total = 0;
        HttpRequest req = HttpRequest.newBuilder(URI.create(serviceURL+"/SumaCantidadPoblacionByProvinciaId/"+idProvincia)).GET().build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(req, BodyHandlers.ofString());
        response.thenAccept(res -> System.out.println(res));

        if(response.get().statusCode() == 500)
        System.out.println("Provincia No Encontrada");

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
    
    public static Double getSumaAreaCuadradaByProvinciaId(long idProvincia) throws InterruptedException, ExecutionException, IOException
    {
        double total = 0;
        HttpRequest req = HttpRequest.newBuilder(URI.create(serviceURL+"/SumaAreaCuadradaByProvinciaId/"+idProvincia)).GET().build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(req, BodyHandlers.ofString());
        response.thenAccept(res -> System.out.println(res));

        if(response.get().statusCode() == 500)
            System.out.println("Provincia No Encontrada");

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
    
    public static List<CantonDTO> getCantonByProvinciaId(long id) throws InterruptedException, ExecutionException, JsonParseException, JsonMappingException, IOException
    {
        HttpRequest req = HttpRequest.newBuilder(URI.create(serviceURL+"/findCantonById/"+id)).GET().build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(req, BodyHandlers.ofString());
        response.thenAccept(res -> System.out.println(res));
        List<CantonDTO> cantones = JSONUtils.convertFromJsonToList(response.get().body(), new TypeReference<List<CantonDTO>>() {});
        cantones.forEach(System.out::println);
        response.join();
        return cantones;
    }

    public static void createProvincia(String nombreProvincia, Integer codigoProvincia) throws InterruptedException, ExecutionException, JsonProcessingException
    {
        ProvinciaDTO bean = new ProvinciaDTO();
        
        bean.setNombreProvincia(nombreProvincia);
        bean.setCodigoProvincia(codigoProvincia);

        String inputJson = JSONUtils.covertFromObjectToJson(bean);
        HttpRequest request = HttpRequest.newBuilder(URI.create(serviceURL+"/"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(inputJson)).build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(request,HttpResponse.BodyHandlers.ofString());
        System.out.println(response.get().body());

    }

    public static void updateProvincia(ProvinciaDTO bean, long id) throws InterruptedException, ExecutionException, IOException
    {
        String inputJson=JSONUtils.covertFromObjectToJson(bean);
        HttpRequest request = HttpRequest.newBuilder(URI.create(serviceURL+"/"+id))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(inputJson)).build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(request,HttpResponse.BodyHandlers.ofString());

        if(response.get().statusCode() == 500)
            System.out.println("No se pudo actualizar la Provincia");

        else {
            bean = JSONUtils.covertFromJsonToObject(response.get().body(), ProvinciaDTO.class);
            System.out.println(bean);
        }
        response.join();
    }
    
    public static void deleteProvincia(long id) throws ExecutionException, InterruptedException, IOException
    {   
        HttpRequest req = HttpRequest.newBuilder(URI.create(serviceURL+"/deleteById/"+id)).DELETE().build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(req, BodyHandlers.ofString());
        response.thenAccept(res -> System.out.println(res));

        if(response.get().statusCode() == 500)
            System.out.println("Provincia No Encontrada");

        else
        {
            System.out.println("Provincia eliminada");
        }
    } 
}
