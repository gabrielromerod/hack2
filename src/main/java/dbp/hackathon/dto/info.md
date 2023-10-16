# DTO (Data Transfer Object)

Nota: Los DTOs son clases que se utilizan para transferir datos entre capas. No deben contener lógica de negocio, solo deben contener atributos y sus getters y setters.

Los DTO nos permiten controlar la información que se envía al cliente, evitando así que se envíen datos sensibles.

Para implementar un DTO debemos escojer los atributos de la clase original que queremos mostrar y crear una clase con esos atributos. Por ejemplo, si queremos mostrar una vista previa de una canción, podemos crear un DTO con los siguientes atributos:

```java
public class SongPreviewDTO {

    private Integer songId;
    private String title;
    private String artist;
    private Integer duration;
    private String spotifyUrl;
    
    // Getters and Setters with Constructor
```

Ahora con nuestro DTO usando MapStruct podemos mapear los atributos de la clase original a los atributos del DTO:

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
```

Lo que estamos haciendo es mapear los atributos de la clase Song a los atributos de la clase SongPreviewDTO. Para esto debemos tener en cuenta que los nombres de los atributos deben ser iguales en ambas clases.