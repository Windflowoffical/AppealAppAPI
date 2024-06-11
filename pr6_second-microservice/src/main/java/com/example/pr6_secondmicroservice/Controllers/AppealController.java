package com.example.pr6_secondmicroservice.Controllers;

import com.example.pr6_secondmicroservice.Models.Appeal;
import com.example.pr6_secondmicroservice.Models.Status;
import com.example.pr6_secondmicroservice.Models.UserDTO;
import com.example.pr6_secondmicroservice.Repositories.AppealRepository;
import com.example.pr6_secondmicroservice.Services.AppealServiceClient;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class AppealController {

    @Autowired
    private AppealRepository appealRepository;

    @Autowired
    private AppealServiceClient appealServiceClient;

    @PutMapping("appeals/{id}")
    public ResponseEntity<?> UpdateStatus (@PathVariable Long id, @RequestBody Appeal appeal) {
        Appeal appealfordb = new Appeal(appealRepository.findById(id).get().getId(), appealRepository.findById(id).get().getTitle(),
                appealRepository.findById(id).get().getDescription(),
                appealRepository.findById(id).get().getUser(),
                appeal.getStatus());
        appealRepository.save(appealfordb);
        return ResponseEntity.ok().body("Статус заявки успешно обновлён");
    }

    @PostMapping("/appeals/create")
    public ResponseEntity<String> CreateAppeal (@RequestBody Appeal appeal) {
        appeal.setStatus(Status.ACCEPTED_FOR_WORK);
        UserDTO userDTO = appealServiceClient.GetUserById(appeal.getUser().getId());
        if (!(userDTO == null)) {
            appealRepository.save(appeal);
            return ResponseEntity.ok().body("Ваша заявка успешно сохранена!");
        } else {
            return ResponseEntity.ok().body("Пользователя с таким ID не существует!");
        }
    }

    @GetMapping("/appeals/{id}")
    public ResponseEntity<?> GetAppealById(@PathVariable Long id) {
        if(appealRepository.findById(id).isPresent()) {
            Optional<Appeal> appealfromdb = appealRepository.findById(id);
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(appealfromdb);
        } else {
            return ResponseEntity.ok().body("Заявки с таким id = " + id + " не существует!");
        }
    }


    @GetMapping("/appeals/get_all")
    public ResponseEntity<?> GetAllAppeal() {
        List<Appeal> all_appeals = appealRepository.findAll();
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(all_appeals);
    }

    @GetMapping("/appeals/get_all_by_user_id/{id}")
    public ResponseEntity<?> GetAllAppealsByUserId(@PathVariable Long id) {
        List<Appeal> all_appeals_by_user_id = appealRepository.findByUserId(id);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(all_appeals_by_user_id);
    }
}
