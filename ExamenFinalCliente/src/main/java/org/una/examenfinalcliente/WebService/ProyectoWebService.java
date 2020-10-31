package org.una.examenfinalcliente.WebService;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import org.una.examenfinalcliente.DTOs.ProyectoDTO;
import org.una.examenfinalcliente.utility.JSONUtils;

/**
 *
 * @author Esteban Vargas
 */
public class ProyectoWebService {
    
    private static final HttpClient client = HttpClient.newBuilder().version(Version.HTTP_2).build();
    private static final String serviceURL = "http://localhost:8090/proyectos";
     
     public static List<ProyectoDTO> getAllProyectos() throws InterruptedException, ExecutionException, JsonParseException, JsonMappingException, IOException
    {
        HttpRequest req = HttpRequest.newBuilder(URI.create(serviceURL+"/findAll")).GET().build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(req, BodyHandlers.ofString());
        response.thenAccept(res -> System.out.println(res));
        List<ProyectoDTO> proyectos = JSONUtils.convertFromJsonToList(response.get().body(), new TypeReference<List<ProyectoDTO>>() {});
        proyectos.forEach(System.out::println);
      //  response.join();
        return proyectos;
    }
     
    public static void createTarea(String descripcion, Date fechaInicio, Date fechaFinalizacion,
    Short importancia, Short urgencia, Integer prioridad, Integer porcentajeAvance, ProyectoDTO proyecto) throws InterruptedException, ExecutionException, JsonProcessingException
    {
        ProyectoDTO bean = new ProyectoDTO();
        
        bean.setNombreProyecto(descripcion);
        bean.setEncargado(descripcion);
        bean.setFechaInicio(fechaInicio);
        bean.setFechaFinalizacion(fechaFinalizacion);

        String inputJson = JSONUtils.covertFromObjectToJson(bean);
        HttpRequest request = HttpRequest.newBuilder(URI.create(serviceURL+"/"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(inputJson)).build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(request,HttpResponse.BodyHandlers.ofString());
        System.out.println(response.get().body());

    }

    public static void updateTarea(ProyectoDTO bean, long id) throws InterruptedException, ExecutionException, IOException
    {
        String inputJson=JSONUtils.covertFromObjectToJson(bean);
        HttpRequest request = HttpRequest.newBuilder(URI.create(serviceURL+"/"+id))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(inputJson)).build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(request,HttpResponse.BodyHandlers.ofString());

        if(response.get().statusCode() == 500)
            System.out.println("No se pudo actualizar el Proyecto");

        else {
            bean = JSONUtils.covertFromJsonToObject(response.get().body(), ProyectoDTO.class);
            System.out.println(bean);
        }
        response.join();
    }
}
