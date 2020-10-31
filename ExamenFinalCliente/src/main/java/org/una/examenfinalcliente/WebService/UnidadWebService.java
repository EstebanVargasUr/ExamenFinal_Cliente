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
import org.una.examenfinalcliente.DTOs.UnidadDTO;
/**
 *
 * @author Adrian
 */
public class UnidadWebService {
    
    private static final HttpClient client = HttpClient.newBuilder().version(Version.HTTP_2).build();
    private static final String serviceURL = "http://localhost:8099/exa_adr_unidades";
    
    public static void getAllUnidades() throws InterruptedException, ExecutionException, JsonParseException, JsonMappingException, IOException
    {
        HttpRequest req = HttpRequest.newBuilder(URI.create(serviceURL+"/findAll")).GET().build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(req, BodyHandlers.ofString());
        response.thenAccept(res -> System.out.println(res));
        List<UnidadDTO> unidades = JSONUtils.convertFromJsonToList(response.get().body(), new TypeReference<List<UnidadDTO>>() {});
        unidades.forEach(System.out::println);
        response.join();
    }

    public static UnidadDTO getUnidadById(long id) throws InterruptedException, ExecutionException, IOException
    {
        UnidadDTO bean = null;
        HttpRequest req = HttpRequest.newBuilder(URI.create(serviceURL+"/findById/"+id)).GET().build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(req, BodyHandlers.ofString());
        response.thenAccept(res -> System.out.println(res));

        if(response.get().statusCode() == 500)
            System.out.println("Unidad No Encontrada");

        else
        {
            bean = JSONUtils.covertFromJsonToObject(response.get().body(), UnidadDTO.class);
            System.out.println(bean);
        }
        response.join();
        return bean;
    }
    
    public static List<UnidadDTO> getUnidadByNombreUnidad(String nombre) throws InterruptedException, ExecutionException, IOException
    {
        HttpRequest req = HttpRequest.newBuilder(URI.create(serviceURL+"/findByNombreUnidadAproximateIgnoreCase/"+nombre)).GET().build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(req, BodyHandlers.ofString());
        response.thenAccept(res -> System.out.println(res));
        List<UnidadDTO> unidades = JSONUtils.convertFromJsonToList(response.get().body(), new TypeReference<List<UnidadDTO>>() {});
        unidades.forEach(System.out::println);
        response.join();
        return unidades;
    }
    
    public static UnidadDTO getUnidadByCodigoUnidad(Integer codigo) throws InterruptedException, ExecutionException, IOException
    {
        UnidadDTO bean = null;
        HttpRequest req = HttpRequest.newBuilder(URI.create(serviceURL+"/findByCodigoUnidad/"+codigo)).GET().build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(req, BodyHandlers.ofString());
        response.thenAccept(res -> System.out.println(res));

        if(response.get().statusCode() == 500)
            System.out.println("Unidad No Encontrada");

        else
        {
            bean = JSONUtils.covertFromJsonToObject(response.get().body(), UnidadDTO.class);
            System.out.println(bean);
        }
        response.join();
        return bean;
    }

    public static Long getSumaCantidadPoblacionByUnidadId(long idUnidad) throws InterruptedException, ExecutionException, IOException
    {
        long total = 0;
        HttpRequest req = HttpRequest.newBuilder(URI.create(serviceURL+"/SumaUnidadCantidadPoblacion/"+idUnidad)).GET().build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(req, BodyHandlers.ofString());
        response.thenAccept(res -> System.out.println(res));

        if(response.get().statusCode() == 500)
            System.out.println("Unidad No Encontrada");

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
    
    public static Double getSumaAreaCuadradaByUnidadId(long idUnidad) throws InterruptedException, ExecutionException, IOException
    {
        double total = 0;
        HttpRequest req = HttpRequest.newBuilder(URI.create(serviceURL+"/SumaUnidadAreaCuadrada/"+idUnidad)).GET().build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(req, BodyHandlers.ofString());
        response.thenAccept(res -> System.out.println(res));

        if(response.get().statusCode() == 500)
            System.out.println("Unidad No Encontrada");

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

    public static void createUnidad(String nombreUnidad, Integer codigoUnidad, long id, long poblacion, double area) throws InterruptedException, ExecutionException, JsonProcessingException, IOException
    {
        UnidadDTO bean = new UnidadDTO();
        
        bean.setNombreUnidad(nombreUnidad);
        bean.setCodigoUnidad(codigoUnidad);
        bean.setCantidadPoblacion(poblacion);
        bean.setAreaEnMetrosCuadrados(area);
        bean.setDistrito(DistritoWebService.getDistritoById(id));

        String inputJson = JSONUtils.covertFromObjectToJson(bean);
        HttpRequest request = HttpRequest.newBuilder(URI.create(serviceURL+"/"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(inputJson)).build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(request,HttpResponse.BodyHandlers.ofString());
        System.out.println(response.get().body());

    }

    public static void updateUnidad(UnidadDTO bean, long id) throws InterruptedException, ExecutionException, IOException
    {
        String inputJson=JSONUtils.covertFromObjectToJson(bean);
        HttpRequest request = HttpRequest.newBuilder(URI.create(serviceURL+"/"+id))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(inputJson)).build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(request,HttpResponse.BodyHandlers.ofString());

        if(response.get().statusCode() == 500)
            System.out.println("No se pudo actualizar la Unidad");

        else {
            bean = JSONUtils.covertFromJsonToObject(response.get().body(), UnidadDTO.class);
            System.out.println(bean);
        }
        response.join();
    }
    
    public static void deleteUnidad(long id) throws ExecutionException, InterruptedException, IOException
    {   
        HttpRequest req = HttpRequest.newBuilder(URI.create(serviceURL+"/deleteById/"+id)).DELETE().build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(req, BodyHandlers.ofString());
        response.thenAccept(res -> System.out.println(res));

        if(response.get().statusCode() == 500)
            System.out.println("Unidad No Encontrada");

        else
        {
            System.out.println("Unidad eliminada");
        }
    } 

}

