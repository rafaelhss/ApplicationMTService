package com.elcartero.service.impl;

import com.elcartero.service.MessageMTService;
import com.elcartero.domain.MessageMT;
import com.elcartero.repository.MessageMTRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing MessageMT.
 */
@Service
@Transactional
public class MessageMTServiceImpl implements MessageMTService{

    private final Logger log = LoggerFactory.getLogger(MessageMTServiceImpl.class);
    
    private final MessageMTRepository messageMTRepository;

    public MessageMTServiceImpl(MessageMTRepository messageMTRepository) {
        this.messageMTRepository = messageMTRepository;
    }

    /**
     * Save a messageMT.
     *
     * @param messageMT the entity to save
     * @return the persisted entity
     */
    @Override
    public MessageMT save(MessageMT messageMT) {
        log.debug("Request to save MessageMT : {}", messageMT);
        MessageMT result = messageMTRepository.save(messageMT);
        return result;
    }

    /**
     *  Get all the messageMTS.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MessageMT> findAll(Pageable pageable) {
        log.debug("Request to get all MessageMTS");
        Page<MessageMT> result = messageMTRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one messageMT by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MessageMT findOne(Long id) {
        log.debug("Request to get MessageMT : {}", id);
        MessageMT messageMT = messageMTRepository.findOne(id);
        return messageMT;
    }

    /**
     *  Delete the  messageMT by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MessageMT : {}", id);
        messageMTRepository.delete(id);
    }
}
