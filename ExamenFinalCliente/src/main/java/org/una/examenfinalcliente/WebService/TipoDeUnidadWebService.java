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
import org.una.examenfinalcliente.DTOs.TipoDeUnidadDTO;
/**
 *
 * @author Adrian
 */
public class TipoDeUnidadWebService {
    
    private static final HttpClient client = HttpClient.newBuilder().version(Version.HTTP_2).build();
    private static final String serviceURL = "http://localhost:8099/exa_adr_tiposDeUnidades";
    
    public static void getAllTipoDeUnidades() throws InterruptedException, ExecutionException, JsonParseException, JsonMappingException, IOException
    {
        HttpRequest req = HttpRequest.newBuilder(URI.create(serviceURL+"/findAll")).GET().build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(req, BodyHandlers.ofString());
        response.thenAccept(res -> System.out.println(res));
        List<TipoDeUnidadDTO> tipoDeUnidad = JSONUtils.convertFromJsonToList(response.get().body(), new TypeReference<List<TipoDeUnidadDTO>>() {});
        tipoDeUnidad.forEach(System.out::println);
        response.join();
    }

    public static void getTipoDeUnidadById(long id) throws InterruptedException, ExecutionException, IOException
    {
        HttpRequest req = HttpRequest.newBuilder(URI.create(serviceURL+"/findById/"+id)).GET().build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(req, BodyHandlers.ofString());
        response.thenAccept(res -> System.out.println(res));

        if(response.get().statusCode() == 500)
            System.out.println("TipoDeUnidad No Encontrado");

        else
        {
            TipoDeUnidadDTO bean = JSONUtils.covertFromJsonToObject(response.get().body(), TipoDeUnidadDTO.class);
            System.out.println(bean);
        }
        response.join();
    }
    
    public static void getTipoDeUnidadByNombreTipoDeUnidad(String nombre) throws InterruptedException, ExecutionException, IOException
    {
        HttpRequest req = HttpRequest.newBuilder(URI.create(serviceURL+"/findByNombreTipoDeUnidadAproximateIgnoreCase/"+nombre)).GET().build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(req, BodyHandlers.ofString());
        response.thenAccept(res -> System.out.println(res));
        List<TipoDeUnidadDTO> tipoDeUnidad = JSONUtils.convertFromJsonToList(response.get().body(), new TypeReference<List<TipoDeUnidadDTO>>() {});
        tipoDeUnidad.forEach(System.out::println);
        response.join();
    } 

    public static void createTipoDeUnidad(String nombreTipoDeUnidad, Integer codigoTipoDeUnidad) throws InterruptedException, ExecutionException, JsonProcessingException
    {
        TipoDeUnidadDTO bean = new TipoDeUnidadDTO();
        
        bean.setNombreTipoUnidad(nombreTipoDeUnidad);
      
        String inputJson = JSONUtils.covertFromObjectToJson(bean);
        HttpRequest request = HttpRequest.newBuilder(URI.create(serviceURL+"/"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(inputJson)).build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(request,HttpResponse.BodyHandlers.ofString());
        System.out.println(response.get().body());

    }

    public static void updateTipoDeUnidad(TipoDeUnidadDTO bean, long id) throws InterruptedException, ExecutionException, IOException
    {
        String inputJson=JSONUtils.covertFromObjectToJson(bean);
        HttpRequest request = HttpRequest.newBuilder(URI.create(serviceURL+"/"+id))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(inputJson)).build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(request,HttpResponse.BodyHandlers.ofString());

        if(response.get().statusCode() == 500)
            System.out.println("No se pudo actualizar el TipoDeUnidad");

        else {
            bean = JSONUtils.covertFromJsonToObject(response.get().body(), TipoDeUnidadDTO.class);
            System.out.println(bean);
        }
        response.join();
    }
    
 
    public static void deleteTipoDeUnidad(long id) throws ExecutionException, InterruptedException, IOException
    {   
        HttpRequest req = HttpRequest.newBuilder(URI.create(serviceURL+"/deleteById/"+id)).DELETE().build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(req, BodyHandlers.ofString());
        response.thenAccept(res -> System.out.println(res));

        if(response.get().statusCode() == 500)
            System.out.println("Tipo de unidad No Encontrada");

        else
        {
            System.out.println("Tipo de unidad eliminada");
        }
    } 
}
