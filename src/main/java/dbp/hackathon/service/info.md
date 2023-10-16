# Service

Los servicios son clases que contienen la lógica de negocio de la aplicación. Estas clases son utilizadas para realizar operaciones en la base de datos.

Es nuestra cocina donde se hace toda la lógica de negocio. Por ejemplo, si queremos crear un nuevo estudiante, el servicio es el encargado de crearlo en la base de datos usando el repositorio y devolver el estudiante creado para que el controlador lo devuelva como respuesta.

Nota: Usa la anotación **@Service** para indicar que una clase es un servicio. Usa la anotación **@Autowired** para inyectar un repositorio en el servicio.

Nota: Usa **@Valid** para validar los datos de entrada. Si no lo haces, no se realizará la validación.

## Implementación

```java
@Service
public class SongService {

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private Validator validator;

    public List<Song> getAllSongsBySearchTitle(String title){
        List<Song> songs = songRepository.findByTitleContainingIgnoreCase(title);
        if(songs.isEmpty()){
            throw new SongNotFoundExeception("No hay resultados para la busqueda de: " + title);
        }
        return songs;
    }

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

    public List<Song> getSongGeneral(){
        List<Song> song = songRepository.findAll();
        if (song.isEmpty()){
            throw new SongNotFoundExeception("No hay canciones ");
        }
        return song;
    }

    public Song saveSong(@Valid Song song){
        Set<ConstraintViolation<Song>> violations = validator.validate(song);
        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            sb.append("Canción inválida, ha olvidado estos parámetros: ");
            for (ConstraintViolation<Song> violation : violations) {
                sb.append(violation.getPropertyPath()).append(", ");
            }
            sb.setLength(sb.length() - 2);
            throw new InvalidSongDataException(sb.toString());
        }
        return songRepository.save(song);
    }

    public Song updateSong(Integer id, @Valid Song updateSong) {
        try{
            Song existingSong = songRepository.findById(id).orElseThrow(() -> new SongNotFoundExeception("Canción no encontrada"));
            BeanUtils.copyProperties(updateSong, existingSong, "songId");
            return songRepository.save(existingSong);
        } catch (Exception e){
            throw new InvalidSongDataException("Canción inválida");
        }
    }

    public Song patchSong(Integer id, Map<String, Object> fields) {
        Song existingSong = songRepository.findById(id).orElseThrow(() -> new InvalidSongDataException("Canción no encontrada"));

        for (String field : fields.keySet()) {
            switch (field) {
                case "title":
                    if (fields.get("title") == null || ((String) fields.get("title")).isEmpty()) {
                        throw new InvalidSongDataException("El título no puede estar vacío");
                    }
                    existingSong.setTitle((String) fields.get("title"));
                    break;

                case "artist":
                    if (fields.get("artist") == null || ((String) fields.get("artist")).isEmpty()) {
                        throw new InvalidSongDataException("El artista no puede estar vacío");
                    }
                    existingSong.setArtist((String) fields.get("artist"));
                    break;

                case "album":
                    if (fields.get("album") == null || ((String) fields.get("album")).isEmpty()) {
                        throw new InvalidSongDataException("El álbum no puede estar vacío");
                    }
                    existingSong.setAlbum((String) fields.get("album"));
                    break;

                case "releaseDate":
                    if (fields.get("releaseDate") == null) {
                        throw new InvalidSongDataException("La fecha de lanzamiento es requerida");
                    }
                    String dateString = (String) fields.get("releaseDate");
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    java.util.Date parsedDate;
                    try {
                        parsedDate = format.parse(dateString);
                        java.sql.Date sqlDate = new java.sql.Date(parsedDate.getTime());
                        existingSong.setReleaseDate(sqlDate);
                    } catch (ParseException e) {
                        throw new InvalidSongDataException("Formato de fecha inválido. Use yyyy-MM-dd.");
                    }
                    break;

                case "genre":
                    if (fields.get("genre") == null || ((String) fields.get("genre")).isEmpty()) {
                        throw new InvalidSongDataException("El género no puede estar vacío");
                    }
                    existingSong.setGenre((String) fields.get("genre"));
                    break;

                case "duration":
                    try {
                        Integer duration = Integer.parseInt((String) fields.get("duration"));
                        existingSong.setDuration(duration);
                    } catch (NumberFormatException e) {
                        throw new InvalidSongDataException("Duración inválida. Debe ser un número entero.");
                    }
                    break;


                case "coverImage":
                    if (fields.get("coverImage") == null || ((String) fields.get("coverImage")).isEmpty()) {
                        throw new InvalidSongDataException("La imagen de portada no puede estar vacía");
                    }
                    existingSong.setCoverImage((String) fields.get("coverImage"));
                    break;

                case "spotifyUrl":
                    if (fields.get("spotifyUrl") == null || ((String) fields.get("spotifyUrl")).isEmpty()) {
                        throw new InvalidSongDataException("La URL de Spotify no puede estar vacía");
                    }
                    existingSong.setSpotifyUrl((String) fields.get("spotifyUrl"));
                    break;

                default:
                    throw new InvalidSongDataException("Campo no reconocido: " + field);
            }
        }
        return songRepository.save(existingSong);
    }
}
```

