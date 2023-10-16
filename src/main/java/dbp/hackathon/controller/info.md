# Controller

Nota: Recuerda en esta clase no debes implementar la lógica de negocio, solo debes llamar a los métodos del servicio que se encargan de ello.

El controlador es el encargado de recibir las peticiones del cliente y de enviar las respuestas al cliente. Para ello, el controlador se comunica con el servicio, que es el encargado de realizar las operaciones necesarias para responder a la petición del cliente.

Para iniciar el controlador, se debe crear una clase que herede de la clase `Controller` y que implemente los métodos `get`, `post`, `put` y `delete` según sea necesario. Estos métodos reciben como parámetro un objeto de tipo `Request` y devuelven un objeto de tipo `Response`.

Para pasar parámetros a la petición, se debe utilizar la anotación `@RequestParam` para parámetros de tipo `GET`, `@RequestBody` para parámetros de tipo `POST`, `PUT` y `DELETE` y `@PathVariable` para parámetros de tipo `GET`, `POST`, `PUT` y `DELETE` que se encuentren en la ruta.

Mientras que para devolver la respuesta, se debe utilizar la clase `ResponseEntity` y el método `ok` para devolver una respuesta con código `200` o `badRequest` para devolver una respuesta con código `400`.

Ejemplo:

```java
@RestController // Indicamos que esta clase es un controlador
@RequestMapping("/api") // Indicamos la ruta base de la API
public class SongController { 

    @Autowired 
    private SongService songService; // Inyectamos el servicio

    @GetMapping // Indicamos que este método responde a una petición GET
    public ResponseEntity<List<Song>> getSong(){
        List<Song> song = songService.getSongGeneral();
        return ResponseEntity.ok(song);
    }

    @PostMapping // Indicamos que este método responde a una petición POST
    public ResponseEntity<Song> createSong(@RequestBody Song song){
        Song song1 = songService.saveSong(song);
        return ResponseEntity.ok(song);
    }
}
```

