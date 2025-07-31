package org.example.demo_docker2.controller;


import org.example.demo_docker2.model.Dog;
import org.example.demo_docker2.repository.DogRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/dogs")
public class DogController {

    private final DogRepository dogRepository;

    public DogController(DogRepository dogRepository) {
        this.dogRepository = dogRepository;
    }

    // GET /api/v1/dogs
    @GetMapping
    public List<Dog> getAllDogs() {
        return dogRepository.findAll();
    }

    // GET /api/dogs/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Dog> getDogById(@PathVariable Long id) {
        return dogRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/dogs
    @PostMapping
    public Dog createDog(@RequestBody Dog dog) {
        return dogRepository.save(dog);
    }

    // PUT /api/v1/dogs/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Dog> updateDog(@PathVariable Long id, @RequestBody Dog newDog) {
        return dogRepository.findById(id)
                .map(dog -> {
                    dog.setName(newDog.getName());
                    dog.setBreed(newDog.getBreed());
                    dog.setAge(newDog.getAge());
                    return ResponseEntity.ok(dogRepository.save(dog));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDog(@PathVariable Long id) {
        return dogRepository.findById(id)
                .map(dog -> {
                    dogRepository.deleteById(dog.getId());
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }




}
