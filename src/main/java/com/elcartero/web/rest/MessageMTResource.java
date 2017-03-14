package com.elcartero.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.elcartero.domain.MessageMT;
import com.elcartero.service.MessageMTService;
import com.elcartero.web.rest.util.HeaderUtil;
import com.elcartero.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing MessageMT.
 */
@RestController
@RequestMapping("/api")
public class MessageMTResource {

    private final Logger log = LoggerFactory.getLogger(MessageMTResource.class);

    private static final String ENTITY_NAME = "messageMT";
        
    private final MessageMTService messageMTService;

    public MessageMTResource(MessageMTService messageMTService) {
        this.messageMTService = messageMTService;
    }

    /**
     * POST  /message-mts : Create a new messageMT.
     *
     * @param messageMT the messageMT to create
     * @return the ResponseEntity with status 201 (Created) and with body the new messageMT, or with status 400 (Bad Request) if the messageMT has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/message-mts")
    @Timed
    public ResponseEntity<MessageMT> createMessageMT(@Valid @RequestBody MessageMT messageMT) throws URISyntaxException {
        log.debug("REST request to save MessageMT : {}", messageMT);
        if (messageMT.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new messageMT cannot already have an ID")).body(null);
        }
        MessageMT result = messageMTService.save(messageMT);
        return ResponseEntity.created(new URI("/api/message-mts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /message-mts : Updates an existing messageMT.
     *
     * @param messageMT the messageMT to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated messageMT,
     * or with status 400 (Bad Request) if the messageMT is not valid,
     * or with status 500 (Internal Server Error) if the messageMT couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/message-mts")
    @Timed
    public ResponseEntity<MessageMT> updateMessageMT(@Valid @RequestBody MessageMT messageMT) throws URISyntaxException {
        log.debug("REST request to update MessageMT : {}", messageMT);
        if (messageMT.getId() == null) {
            return createMessageMT(messageMT);
        }
        MessageMT result = messageMTService.save(messageMT);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, messageMT.getId().toString()))
            .body(result);
    }

    /**
     * GET  /message-mts : get all the messageMTS.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of messageMTS in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/message-mts")
    @Timed
    public ResponseEntity<List<MessageMT>> getAllMessageMTS(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of MessageMTS");
        Page<MessageMT> page = messageMTService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/message-mts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /message-mts/:id : get the "id" messageMT.
     *
     * @param id the id of the messageMT to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the messageMT, or with status 404 (Not Found)
     */
    @GetMapping("/message-mts/{id}")
    @Timed
    public ResponseEntity<MessageMT> getMessageMT(@PathVariable Long id) {
        log.debug("REST request to get MessageMT : {}", id);
        MessageMT messageMT = messageMTService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(messageMT));
    }

    /**
     * DELETE  /message-mts/:id : delete the "id" messageMT.
     *
     * @param id the id of the messageMT to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/message-mts/{id}")
    @Timed
    public ResponseEntity<Void> deleteMessageMT(@PathVariable Long id) {
        log.debug("REST request to delete MessageMT : {}", id);
        messageMTService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
