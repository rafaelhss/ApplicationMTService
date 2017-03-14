package com.elcartero.web.rest;

import com.elcartero.ApplicationMtServiceApp;

import com.elcartero.domain.MessageMT;
import com.elcartero.repository.MessageMTRepository;
import com.elcartero.service.MessageMTService;
import com.elcartero.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MessageMTResource REST controller.
 *
 * @see MessageMTResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationMtServiceApp.class)
public class MessageMTResourceIntTest {

    private static final String DEFAULT_DESTINATION = "AAAAAAAAAA";
    private static final String UPDATED_DESTINATION = "BBBBBBBBBB";

    private static final String DEFAULT_SOURCE = "AAAAAAAAAA";
    private static final String UPDATED_SOURCE = "BBBBBBBBBB";

    private static final String DEFAULT_BODY = "AAAAAAAAAA";
    private static final String UPDATED_BODY = "BBBBBBBBBB";

    private static final String DEFAULT_SECRET_TOKEN = "AAAAAAAAAA";
    private static final String UPDATED_SECRET_TOKEN = "BBBBBBBBBB";

    @Autowired
    private MessageMTRepository messageMTRepository;

    @Autowired
    private MessageMTService messageMTService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMessageMTMockMvc;

    private MessageMT messageMT;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MessageMTResource messageMTResource = new MessageMTResource(messageMTService);
        this.restMessageMTMockMvc = MockMvcBuilders.standaloneSetup(messageMTResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MessageMT createEntity(EntityManager em) {
        MessageMT messageMT = new MessageMT()
            .destination(DEFAULT_DESTINATION)
            .source(DEFAULT_SOURCE)
            .body(DEFAULT_BODY)
            .secretToken(DEFAULT_SECRET_TOKEN);
        return messageMT;
    }

    @Before
    public void initTest() {
        messageMT = createEntity(em);
    }

    @Test
    @Transactional
    public void createMessageMT() throws Exception {
        int databaseSizeBeforeCreate = messageMTRepository.findAll().size();

        // Create the MessageMT
        restMessageMTMockMvc.perform(post("/api/message-mts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(messageMT)))
            .andExpect(status().isCreated());

        // Validate the MessageMT in the database
        List<MessageMT> messageMTList = messageMTRepository.findAll();
        assertThat(messageMTList).hasSize(databaseSizeBeforeCreate + 1);
        MessageMT testMessageMT = messageMTList.get(messageMTList.size() - 1);
        assertThat(testMessageMT.getDestination()).isEqualTo(DEFAULT_DESTINATION);
        assertThat(testMessageMT.getSource()).isEqualTo(DEFAULT_SOURCE);
        assertThat(testMessageMT.getBody()).isEqualTo(DEFAULT_BODY);
        assertThat(testMessageMT.getSecretToken()).isEqualTo(DEFAULT_SECRET_TOKEN);
    }

    @Test
    @Transactional
    public void createMessageMTWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = messageMTRepository.findAll().size();

        // Create the MessageMT with an existing ID
        messageMT.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMessageMTMockMvc.perform(post("/api/message-mts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(messageMT)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<MessageMT> messageMTList = messageMTRepository.findAll();
        assertThat(messageMTList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDestinationIsRequired() throws Exception {
        int databaseSizeBeforeTest = messageMTRepository.findAll().size();
        // set the field null
        messageMT.setDestination(null);

        // Create the MessageMT, which fails.

        restMessageMTMockMvc.perform(post("/api/message-mts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(messageMT)))
            .andExpect(status().isBadRequest());

        List<MessageMT> messageMTList = messageMTRepository.findAll();
        assertThat(messageMTList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSourceIsRequired() throws Exception {
        int databaseSizeBeforeTest = messageMTRepository.findAll().size();
        // set the field null
        messageMT.setSource(null);

        // Create the MessageMT, which fails.

        restMessageMTMockMvc.perform(post("/api/message-mts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(messageMT)))
            .andExpect(status().isBadRequest());

        List<MessageMT> messageMTList = messageMTRepository.findAll();
        assertThat(messageMTList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBodyIsRequired() throws Exception {
        int databaseSizeBeforeTest = messageMTRepository.findAll().size();
        // set the field null
        messageMT.setBody(null);

        // Create the MessageMT, which fails.

        restMessageMTMockMvc.perform(post("/api/message-mts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(messageMT)))
            .andExpect(status().isBadRequest());

        List<MessageMT> messageMTList = messageMTRepository.findAll();
        assertThat(messageMTList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSecretTokenIsRequired() throws Exception {
        int databaseSizeBeforeTest = messageMTRepository.findAll().size();
        // set the field null
        messageMT.setSecretToken(null);

        // Create the MessageMT, which fails.

        restMessageMTMockMvc.perform(post("/api/message-mts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(messageMT)))
            .andExpect(status().isBadRequest());

        List<MessageMT> messageMTList = messageMTRepository.findAll();
        assertThat(messageMTList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMessageMTS() throws Exception {
        // Initialize the database
        messageMTRepository.saveAndFlush(messageMT);

        // Get all the messageMTList
        restMessageMTMockMvc.perform(get("/api/message-mts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(messageMT.getId().intValue())))
            .andExpect(jsonPath("$.[*].destination").value(hasItem(DEFAULT_DESTINATION.toString())))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE.toString())))
            .andExpect(jsonPath("$.[*].body").value(hasItem(DEFAULT_BODY.toString())))
            .andExpect(jsonPath("$.[*].secretToken").value(hasItem(DEFAULT_SECRET_TOKEN.toString())));
    }

    @Test
    @Transactional
    public void getMessageMT() throws Exception {
        // Initialize the database
        messageMTRepository.saveAndFlush(messageMT);

        // Get the messageMT
        restMessageMTMockMvc.perform(get("/api/message-mts/{id}", messageMT.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(messageMT.getId().intValue()))
            .andExpect(jsonPath("$.destination").value(DEFAULT_DESTINATION.toString()))
            .andExpect(jsonPath("$.source").value(DEFAULT_SOURCE.toString()))
            .andExpect(jsonPath("$.body").value(DEFAULT_BODY.toString()))
            .andExpect(jsonPath("$.secretToken").value(DEFAULT_SECRET_TOKEN.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMessageMT() throws Exception {
        // Get the messageMT
        restMessageMTMockMvc.perform(get("/api/message-mts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMessageMT() throws Exception {
        // Initialize the database
        messageMTService.save(messageMT);

        int databaseSizeBeforeUpdate = messageMTRepository.findAll().size();

        // Update the messageMT
        MessageMT updatedMessageMT = messageMTRepository.findOne(messageMT.getId());
        updatedMessageMT
            .destination(UPDATED_DESTINATION)
            .source(UPDATED_SOURCE)
            .body(UPDATED_BODY)
            .secretToken(UPDATED_SECRET_TOKEN);

        restMessageMTMockMvc.perform(put("/api/message-mts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMessageMT)))
            .andExpect(status().isOk());

        // Validate the MessageMT in the database
        List<MessageMT> messageMTList = messageMTRepository.findAll();
        assertThat(messageMTList).hasSize(databaseSizeBeforeUpdate);
        MessageMT testMessageMT = messageMTList.get(messageMTList.size() - 1);
        assertThat(testMessageMT.getDestination()).isEqualTo(UPDATED_DESTINATION);
        assertThat(testMessageMT.getSource()).isEqualTo(UPDATED_SOURCE);
        assertThat(testMessageMT.getBody()).isEqualTo(UPDATED_BODY);
        assertThat(testMessageMT.getSecretToken()).isEqualTo(UPDATED_SECRET_TOKEN);
    }

    @Test
    @Transactional
    public void updateNonExistingMessageMT() throws Exception {
        int databaseSizeBeforeUpdate = messageMTRepository.findAll().size();

        // Create the MessageMT

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMessageMTMockMvc.perform(put("/api/message-mts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(messageMT)))
            .andExpect(status().isCreated());

        // Validate the MessageMT in the database
        List<MessageMT> messageMTList = messageMTRepository.findAll();
        assertThat(messageMTList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMessageMT() throws Exception {
        // Initialize the database
        messageMTService.save(messageMT);

        int databaseSizeBeforeDelete = messageMTRepository.findAll().size();

        // Get the messageMT
        restMessageMTMockMvc.perform(delete("/api/message-mts/{id}", messageMT.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MessageMT> messageMTList = messageMTRepository.findAll();
        assertThat(messageMTList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MessageMT.class);
    }
}
