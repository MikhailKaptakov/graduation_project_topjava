package ru.graduation_project_topjava.web.controllers.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.graduation_project_topjava.model.Vote;
import ru.graduation_project_topjava.repository.CrudVoteRepository;
import ru.graduation_project_topjava.util.validation.ValidationUtil;

@RestController
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteController {

    public static final String REST_URL = "/votes";

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private CrudVoteRepository voteRepository;

    @GetMapping("/{id}")
    public Vote getVote (@PathVariable long id) {
        log.debug("get vote: " + id);
        return ValidationUtil.checkNotFoundWithId(voteRepository.findById(id).orElse(null), id);
    }
}
