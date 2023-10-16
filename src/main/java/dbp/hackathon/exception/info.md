# Exception Handling

Tenemos dos tipos de excepciones, las globales y personalizadas. Las globales son las que se lanzan cuando ocurre un error en el servidor, por ejemplo, cuando se envía un parámetro incorrecto en una petición GET. Las personalizadas son las que se lanzan cuando ocurre un error en la lógica de negocio, por ejemplo, cuando se intenta crear una canción con un título que ya existe.

## Exception Personalizadas

Para crear una excepción personalizada debemos crear una clase que extienda de RuntimeException. Por ejemplo, si queremos crear una excepción para cuando no se encuentre una canción, podemos crear la siguiente clase:

```java
public class SongNotFoundExeception extends RuntimeException{

    public SongNotFoundExeception(String message){
        super(message);
    }
}
```

## Exception Globales

Pero como necesitamos que sea global, debemos crear un manejador de excepciones. Para esto creamos una clase que extienda de ResponseEntityExceptionHandler y sobreescribimos el método handleExceptionInternal. Por ejemplo, si queremos crear un manejador para la excepción SongNotFoundExeception, podemos crear la siguiente clase:

Nota: Debes usar la anotación @ControllerAdvice para que Spring sepa que es un manejador de excepciones.

```java
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SongNotFoundExeception.class)
    public ResponseEntity<?> handleSongNotFoundExeception(SongNotFoundExeception text){
        return new ResponseEntity<>(text.getMessage(), HttpStatus.NOT_FOUND);
    }
}

```

Ahora cuando se lance la excepción SongNotFoundExeception, se ejecutará el método handleSongNotFoundExeception y se retornará un mensaje de error con el código 404. De esta manera podemos crear manejadores para todas las excepciones personalizadas que necesitemos.

Un ejemplo usando nuestra excepción SongNotFoundExeception:

```java
@Service
public class SongService {

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private Validator validator;

    public SongPreviewDTO convertEntityDTO(Optional<Song> song){
        SongPreviewDTO songPreviewDTO = new SongPreviewDTO();
        songPreviewDTO = modelMapper.map(song.get(), SongPreviewDTO.class);
        return  songPreviewDTO;
    }

    public SongPreviewDTO getSongPreviewById(Integer id){
        Optional<Song> song = songRepository.findById(id);
        if (!song.isEmpty()){
            return convertEntityDTO(song);
        }
        throw new SongNotFoundExeception("No hay canción valida para el id: " + id);
    }
}
```