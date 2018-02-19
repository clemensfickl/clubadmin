package at.fickl.clubadmin.web.rest;

import at.fickl.clubadmin.ClubadminApp;

import at.fickl.clubadmin.domain.Member;
import at.fickl.clubadmin.repository.MemberRepository;
import at.fickl.clubadmin.service.MemberService;
import at.fickl.clubadmin.service.dto.MemberDTO;
import at.fickl.clubadmin.service.mapper.MemberMapper;
import at.fickl.clubadmin.web.rest.errors.ExceptionTranslator;
import at.fickl.clubadmin.service.dto.MemberCriteria;
import at.fickl.clubadmin.service.MemberQueryService;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static at.fickl.clubadmin.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import at.fickl.clubadmin.domain.enumeration.Sex;
/**
 * Test class for the MemberResource REST controller.
 *
 * @see MemberResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ClubadminApp.class)
public class MemberResourceIntTest {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final Sex DEFAULT_SEX = Sex.MALE;
    private static final Sex UPDATED_SEX = Sex.FEMALE;

    private static final LocalDate DEFAULT_BIRTHDATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BIRTHDATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_ENTRY_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ENTRY_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_TERMINATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TERMINATION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_EXIT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EXIT_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_STREET_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_STREET_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_POSTAL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_POSTAL_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_PROVINCE = "AAAAAAAAAA";
    private static final String UPDATED_PROVINCE = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final Boolean DEFAULT_VOTE = false;
    private static final Boolean UPDATED_VOTE = true;

    private static final Boolean DEFAULT_OERV = false;
    private static final Boolean UPDATED_OERV = true;

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberQueryService memberQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMemberMockMvc;

    private Member member;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MemberResource memberResource = new MemberResource(memberService, memberQueryService);
        this.restMemberMockMvc = MockMvcBuilders.standaloneSetup(memberResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Member createEntity(EntityManager em) {
        Member member = new Member()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .title(DEFAULT_TITLE)
            .sex(DEFAULT_SEX)
            .birthdate(DEFAULT_BIRTHDATE)
            .email(DEFAULT_EMAIL)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .entryDate(DEFAULT_ENTRY_DATE)
            .terminationDate(DEFAULT_TERMINATION_DATE)
            .exitDate(DEFAULT_EXIT_DATE)
            .streetAddress(DEFAULT_STREET_ADDRESS)
            .postalCode(DEFAULT_POSTAL_CODE)
            .city(DEFAULT_CITY)
            .province(DEFAULT_PROVINCE)
            .country(DEFAULT_COUNTRY)
            .vote(DEFAULT_VOTE)
            .oerv(DEFAULT_OERV)
            .comment(DEFAULT_COMMENT);
        return member;
    }

    @Before
    public void initTest() {
        member = createEntity(em);
    }

    @Test
    @Transactional
    public void createMember() throws Exception {
        int databaseSizeBeforeCreate = memberRepository.findAll().size();

        // Create the Member
        MemberDTO memberDTO = memberMapper.toDto(member);
        restMemberMockMvc.perform(post("/api/members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(memberDTO)))
            .andExpect(status().isCreated());

        // Validate the Member in the database
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeCreate + 1);
        Member testMember = memberList.get(memberList.size() - 1);
        assertThat(testMember.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testMember.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testMember.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testMember.getSex()).isEqualTo(DEFAULT_SEX);
        assertThat(testMember.getBirthdate()).isEqualTo(DEFAULT_BIRTHDATE);
        assertThat(testMember.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testMember.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testMember.getEntryDate()).isEqualTo(DEFAULT_ENTRY_DATE);
        assertThat(testMember.getTerminationDate()).isEqualTo(DEFAULT_TERMINATION_DATE);
        assertThat(testMember.getExitDate()).isEqualTo(DEFAULT_EXIT_DATE);
        assertThat(testMember.getStreetAddress()).isEqualTo(DEFAULT_STREET_ADDRESS);
        assertThat(testMember.getPostalCode()).isEqualTo(DEFAULT_POSTAL_CODE);
        assertThat(testMember.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testMember.getProvince()).isEqualTo(DEFAULT_PROVINCE);
        assertThat(testMember.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testMember.isVote()).isEqualTo(DEFAULT_VOTE);
        assertThat(testMember.isOerv()).isEqualTo(DEFAULT_OERV);
        assertThat(testMember.getComment()).isEqualTo(DEFAULT_COMMENT);
    }

    @Test
    @Transactional
    public void createMemberWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = memberRepository.findAll().size();

        // Create the Member with an existing ID
        member.setId(1L);
        MemberDTO memberDTO = memberMapper.toDto(member);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMemberMockMvc.perform(post("/api/members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(memberDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Member in the database
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMembers() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList
        restMemberMockMvc.perform(get("/api/members?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(member.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].sex").value(hasItem(DEFAULT_SEX.toString())))
            .andExpect(jsonPath("$.[*].birthdate").value(hasItem(DEFAULT_BIRTHDATE.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].entryDate").value(hasItem(DEFAULT_ENTRY_DATE.toString())))
            .andExpect(jsonPath("$.[*].terminationDate").value(hasItem(DEFAULT_TERMINATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].exitDate").value(hasItem(DEFAULT_EXIT_DATE.toString())))
            .andExpect(jsonPath("$.[*].streetAddress").value(hasItem(DEFAULT_STREET_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].province").value(hasItem(DEFAULT_PROVINCE.toString())))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].vote").value(hasItem(DEFAULT_VOTE.booleanValue())))
            .andExpect(jsonPath("$.[*].oerv").value(hasItem(DEFAULT_OERV.booleanValue())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())));
    }

    @Test
    @Transactional
    public void getMember() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get the member
        restMemberMockMvc.perform(get("/api/members/{id}", member.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(member.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.sex").value(DEFAULT_SEX.toString()))
            .andExpect(jsonPath("$.birthdate").value(DEFAULT_BIRTHDATE.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER.toString()))
            .andExpect(jsonPath("$.entryDate").value(DEFAULT_ENTRY_DATE.toString()))
            .andExpect(jsonPath("$.terminationDate").value(DEFAULT_TERMINATION_DATE.toString()))
            .andExpect(jsonPath("$.exitDate").value(DEFAULT_EXIT_DATE.toString()))
            .andExpect(jsonPath("$.streetAddress").value(DEFAULT_STREET_ADDRESS.toString()))
            .andExpect(jsonPath("$.postalCode").value(DEFAULT_POSTAL_CODE.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.province").value(DEFAULT_PROVINCE.toString()))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY.toString()))
            .andExpect(jsonPath("$.vote").value(DEFAULT_VOTE.booleanValue()))
            .andExpect(jsonPath("$.oerv").value(DEFAULT_OERV.booleanValue()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()));
    }

    @Test
    @Transactional
    public void getAllMembersByFirstNameIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where firstName equals to DEFAULT_FIRST_NAME
        defaultMemberShouldBeFound("firstName.equals=" + DEFAULT_FIRST_NAME);

        // Get all the memberList where firstName equals to UPDATED_FIRST_NAME
        defaultMemberShouldNotBeFound("firstName.equals=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllMembersByFirstNameIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where firstName in DEFAULT_FIRST_NAME or UPDATED_FIRST_NAME
        defaultMemberShouldBeFound("firstName.in=" + DEFAULT_FIRST_NAME + "," + UPDATED_FIRST_NAME);

        // Get all the memberList where firstName equals to UPDATED_FIRST_NAME
        defaultMemberShouldNotBeFound("firstName.in=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllMembersByFirstNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where firstName is not null
        defaultMemberShouldBeFound("firstName.specified=true");

        // Get all the memberList where firstName is null
        defaultMemberShouldNotBeFound("firstName.specified=false");
    }

    @Test
    @Transactional
    public void getAllMembersByLastNameIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where lastName equals to DEFAULT_LAST_NAME
        defaultMemberShouldBeFound("lastName.equals=" + DEFAULT_LAST_NAME);

        // Get all the memberList where lastName equals to UPDATED_LAST_NAME
        defaultMemberShouldNotBeFound("lastName.equals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllMembersByLastNameIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where lastName in DEFAULT_LAST_NAME or UPDATED_LAST_NAME
        defaultMemberShouldBeFound("lastName.in=" + DEFAULT_LAST_NAME + "," + UPDATED_LAST_NAME);

        // Get all the memberList where lastName equals to UPDATED_LAST_NAME
        defaultMemberShouldNotBeFound("lastName.in=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllMembersByLastNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where lastName is not null
        defaultMemberShouldBeFound("lastName.specified=true");

        // Get all the memberList where lastName is null
        defaultMemberShouldNotBeFound("lastName.specified=false");
    }

    @Test
    @Transactional
    public void getAllMembersByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where title equals to DEFAULT_TITLE
        defaultMemberShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the memberList where title equals to UPDATED_TITLE
        defaultMemberShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllMembersByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultMemberShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the memberList where title equals to UPDATED_TITLE
        defaultMemberShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllMembersByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where title is not null
        defaultMemberShouldBeFound("title.specified=true");

        // Get all the memberList where title is null
        defaultMemberShouldNotBeFound("title.specified=false");
    }

    @Test
    @Transactional
    public void getAllMembersBySexIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where sex equals to DEFAULT_SEX
        defaultMemberShouldBeFound("sex.equals=" + DEFAULT_SEX);

        // Get all the memberList where sex equals to UPDATED_SEX
        defaultMemberShouldNotBeFound("sex.equals=" + UPDATED_SEX);
    }

    @Test
    @Transactional
    public void getAllMembersBySexIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where sex in DEFAULT_SEX or UPDATED_SEX
        defaultMemberShouldBeFound("sex.in=" + DEFAULT_SEX + "," + UPDATED_SEX);

        // Get all the memberList where sex equals to UPDATED_SEX
        defaultMemberShouldNotBeFound("sex.in=" + UPDATED_SEX);
    }

    @Test
    @Transactional
    public void getAllMembersBySexIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where sex is not null
        defaultMemberShouldBeFound("sex.specified=true");

        // Get all the memberList where sex is null
        defaultMemberShouldNotBeFound("sex.specified=false");
    }

    @Test
    @Transactional
    public void getAllMembersByBirthdateIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where birthdate equals to DEFAULT_BIRTHDATE
        defaultMemberShouldBeFound("birthdate.equals=" + DEFAULT_BIRTHDATE);

        // Get all the memberList where birthdate equals to UPDATED_BIRTHDATE
        defaultMemberShouldNotBeFound("birthdate.equals=" + UPDATED_BIRTHDATE);
    }

    @Test
    @Transactional
    public void getAllMembersByBirthdateIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where birthdate in DEFAULT_BIRTHDATE or UPDATED_BIRTHDATE
        defaultMemberShouldBeFound("birthdate.in=" + DEFAULT_BIRTHDATE + "," + UPDATED_BIRTHDATE);

        // Get all the memberList where birthdate equals to UPDATED_BIRTHDATE
        defaultMemberShouldNotBeFound("birthdate.in=" + UPDATED_BIRTHDATE);
    }

    @Test
    @Transactional
    public void getAllMembersByBirthdateIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where birthdate is not null
        defaultMemberShouldBeFound("birthdate.specified=true");

        // Get all the memberList where birthdate is null
        defaultMemberShouldNotBeFound("birthdate.specified=false");
    }

    @Test
    @Transactional
    public void getAllMembersByBirthdateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where birthdate greater than or equals to DEFAULT_BIRTHDATE
        defaultMemberShouldBeFound("birthdate.greaterOrEqualThan=" + DEFAULT_BIRTHDATE);

        // Get all the memberList where birthdate greater than or equals to UPDATED_BIRTHDATE
        defaultMemberShouldNotBeFound("birthdate.greaterOrEqualThan=" + UPDATED_BIRTHDATE);
    }

    @Test
    @Transactional
    public void getAllMembersByBirthdateIsLessThanSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where birthdate less than or equals to DEFAULT_BIRTHDATE
        defaultMemberShouldNotBeFound("birthdate.lessThan=" + DEFAULT_BIRTHDATE);

        // Get all the memberList where birthdate less than or equals to UPDATED_BIRTHDATE
        defaultMemberShouldBeFound("birthdate.lessThan=" + UPDATED_BIRTHDATE);
    }


    @Test
    @Transactional
    public void getAllMembersByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where email equals to DEFAULT_EMAIL
        defaultMemberShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the memberList where email equals to UPDATED_EMAIL
        defaultMemberShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllMembersByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultMemberShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the memberList where email equals to UPDATED_EMAIL
        defaultMemberShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllMembersByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where email is not null
        defaultMemberShouldBeFound("email.specified=true");

        // Get all the memberList where email is null
        defaultMemberShouldNotBeFound("email.specified=false");
    }

    @Test
    @Transactional
    public void getAllMembersByPhoneNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where phoneNumber equals to DEFAULT_PHONE_NUMBER
        defaultMemberShouldBeFound("phoneNumber.equals=" + DEFAULT_PHONE_NUMBER);

        // Get all the memberList where phoneNumber equals to UPDATED_PHONE_NUMBER
        defaultMemberShouldNotBeFound("phoneNumber.equals=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllMembersByPhoneNumberIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where phoneNumber in DEFAULT_PHONE_NUMBER or UPDATED_PHONE_NUMBER
        defaultMemberShouldBeFound("phoneNumber.in=" + DEFAULT_PHONE_NUMBER + "," + UPDATED_PHONE_NUMBER);

        // Get all the memberList where phoneNumber equals to UPDATED_PHONE_NUMBER
        defaultMemberShouldNotBeFound("phoneNumber.in=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllMembersByPhoneNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where phoneNumber is not null
        defaultMemberShouldBeFound("phoneNumber.specified=true");

        // Get all the memberList where phoneNumber is null
        defaultMemberShouldNotBeFound("phoneNumber.specified=false");
    }

    @Test
    @Transactional
    public void getAllMembersByEntryDateIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where entryDate equals to DEFAULT_ENTRY_DATE
        defaultMemberShouldBeFound("entryDate.equals=" + DEFAULT_ENTRY_DATE);

        // Get all the memberList where entryDate equals to UPDATED_ENTRY_DATE
        defaultMemberShouldNotBeFound("entryDate.equals=" + UPDATED_ENTRY_DATE);
    }

    @Test
    @Transactional
    public void getAllMembersByEntryDateIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where entryDate in DEFAULT_ENTRY_DATE or UPDATED_ENTRY_DATE
        defaultMemberShouldBeFound("entryDate.in=" + DEFAULT_ENTRY_DATE + "," + UPDATED_ENTRY_DATE);

        // Get all the memberList where entryDate equals to UPDATED_ENTRY_DATE
        defaultMemberShouldNotBeFound("entryDate.in=" + UPDATED_ENTRY_DATE);
    }

    @Test
    @Transactional
    public void getAllMembersByEntryDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where entryDate is not null
        defaultMemberShouldBeFound("entryDate.specified=true");

        // Get all the memberList where entryDate is null
        defaultMemberShouldNotBeFound("entryDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllMembersByEntryDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where entryDate greater than or equals to DEFAULT_ENTRY_DATE
        defaultMemberShouldBeFound("entryDate.greaterOrEqualThan=" + DEFAULT_ENTRY_DATE);

        // Get all the memberList where entryDate greater than or equals to UPDATED_ENTRY_DATE
        defaultMemberShouldNotBeFound("entryDate.greaterOrEqualThan=" + UPDATED_ENTRY_DATE);
    }

    @Test
    @Transactional
    public void getAllMembersByEntryDateIsLessThanSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where entryDate less than or equals to DEFAULT_ENTRY_DATE
        defaultMemberShouldNotBeFound("entryDate.lessThan=" + DEFAULT_ENTRY_DATE);

        // Get all the memberList where entryDate less than or equals to UPDATED_ENTRY_DATE
        defaultMemberShouldBeFound("entryDate.lessThan=" + UPDATED_ENTRY_DATE);
    }


    @Test
    @Transactional
    public void getAllMembersByTerminationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where terminationDate equals to DEFAULT_TERMINATION_DATE
        defaultMemberShouldBeFound("terminationDate.equals=" + DEFAULT_TERMINATION_DATE);

        // Get all the memberList where terminationDate equals to UPDATED_TERMINATION_DATE
        defaultMemberShouldNotBeFound("terminationDate.equals=" + UPDATED_TERMINATION_DATE);
    }

    @Test
    @Transactional
    public void getAllMembersByTerminationDateIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where terminationDate in DEFAULT_TERMINATION_DATE or UPDATED_TERMINATION_DATE
        defaultMemberShouldBeFound("terminationDate.in=" + DEFAULT_TERMINATION_DATE + "," + UPDATED_TERMINATION_DATE);

        // Get all the memberList where terminationDate equals to UPDATED_TERMINATION_DATE
        defaultMemberShouldNotBeFound("terminationDate.in=" + UPDATED_TERMINATION_DATE);
    }

    @Test
    @Transactional
    public void getAllMembersByTerminationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where terminationDate is not null
        defaultMemberShouldBeFound("terminationDate.specified=true");

        // Get all the memberList where terminationDate is null
        defaultMemberShouldNotBeFound("terminationDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllMembersByTerminationDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where terminationDate greater than or equals to DEFAULT_TERMINATION_DATE
        defaultMemberShouldBeFound("terminationDate.greaterOrEqualThan=" + DEFAULT_TERMINATION_DATE);

        // Get all the memberList where terminationDate greater than or equals to UPDATED_TERMINATION_DATE
        defaultMemberShouldNotBeFound("terminationDate.greaterOrEqualThan=" + UPDATED_TERMINATION_DATE);
    }

    @Test
    @Transactional
    public void getAllMembersByTerminationDateIsLessThanSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where terminationDate less than or equals to DEFAULT_TERMINATION_DATE
        defaultMemberShouldNotBeFound("terminationDate.lessThan=" + DEFAULT_TERMINATION_DATE);

        // Get all the memberList where terminationDate less than or equals to UPDATED_TERMINATION_DATE
        defaultMemberShouldBeFound("terminationDate.lessThan=" + UPDATED_TERMINATION_DATE);
    }


    @Test
    @Transactional
    public void getAllMembersByExitDateIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where exitDate equals to DEFAULT_EXIT_DATE
        defaultMemberShouldBeFound("exitDate.equals=" + DEFAULT_EXIT_DATE);

        // Get all the memberList where exitDate equals to UPDATED_EXIT_DATE
        defaultMemberShouldNotBeFound("exitDate.equals=" + UPDATED_EXIT_DATE);
    }

    @Test
    @Transactional
    public void getAllMembersByExitDateIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where exitDate in DEFAULT_EXIT_DATE or UPDATED_EXIT_DATE
        defaultMemberShouldBeFound("exitDate.in=" + DEFAULT_EXIT_DATE + "," + UPDATED_EXIT_DATE);

        // Get all the memberList where exitDate equals to UPDATED_EXIT_DATE
        defaultMemberShouldNotBeFound("exitDate.in=" + UPDATED_EXIT_DATE);
    }

    @Test
    @Transactional
    public void getAllMembersByExitDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where exitDate is not null
        defaultMemberShouldBeFound("exitDate.specified=true");

        // Get all the memberList where exitDate is null
        defaultMemberShouldNotBeFound("exitDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllMembersByExitDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where exitDate greater than or equals to DEFAULT_EXIT_DATE
        defaultMemberShouldBeFound("exitDate.greaterOrEqualThan=" + DEFAULT_EXIT_DATE);

        // Get all the memberList where exitDate greater than or equals to UPDATED_EXIT_DATE
        defaultMemberShouldNotBeFound("exitDate.greaterOrEqualThan=" + UPDATED_EXIT_DATE);
    }

    @Test
    @Transactional
    public void getAllMembersByExitDateIsLessThanSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where exitDate less than or equals to DEFAULT_EXIT_DATE
        defaultMemberShouldNotBeFound("exitDate.lessThan=" + DEFAULT_EXIT_DATE);

        // Get all the memberList where exitDate less than or equals to UPDATED_EXIT_DATE
        defaultMemberShouldBeFound("exitDate.lessThan=" + UPDATED_EXIT_DATE);
    }


    @Test
    @Transactional
    public void getAllMembersByStreetAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where streetAddress equals to DEFAULT_STREET_ADDRESS
        defaultMemberShouldBeFound("streetAddress.equals=" + DEFAULT_STREET_ADDRESS);

        // Get all the memberList where streetAddress equals to UPDATED_STREET_ADDRESS
        defaultMemberShouldNotBeFound("streetAddress.equals=" + UPDATED_STREET_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllMembersByStreetAddressIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where streetAddress in DEFAULT_STREET_ADDRESS or UPDATED_STREET_ADDRESS
        defaultMemberShouldBeFound("streetAddress.in=" + DEFAULT_STREET_ADDRESS + "," + UPDATED_STREET_ADDRESS);

        // Get all the memberList where streetAddress equals to UPDATED_STREET_ADDRESS
        defaultMemberShouldNotBeFound("streetAddress.in=" + UPDATED_STREET_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllMembersByStreetAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where streetAddress is not null
        defaultMemberShouldBeFound("streetAddress.specified=true");

        // Get all the memberList where streetAddress is null
        defaultMemberShouldNotBeFound("streetAddress.specified=false");
    }

    @Test
    @Transactional
    public void getAllMembersByPostalCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where postalCode equals to DEFAULT_POSTAL_CODE
        defaultMemberShouldBeFound("postalCode.equals=" + DEFAULT_POSTAL_CODE);

        // Get all the memberList where postalCode equals to UPDATED_POSTAL_CODE
        defaultMemberShouldNotBeFound("postalCode.equals=" + UPDATED_POSTAL_CODE);
    }

    @Test
    @Transactional
    public void getAllMembersByPostalCodeIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where postalCode in DEFAULT_POSTAL_CODE or UPDATED_POSTAL_CODE
        defaultMemberShouldBeFound("postalCode.in=" + DEFAULT_POSTAL_CODE + "," + UPDATED_POSTAL_CODE);

        // Get all the memberList where postalCode equals to UPDATED_POSTAL_CODE
        defaultMemberShouldNotBeFound("postalCode.in=" + UPDATED_POSTAL_CODE);
    }

    @Test
    @Transactional
    public void getAllMembersByPostalCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where postalCode is not null
        defaultMemberShouldBeFound("postalCode.specified=true");

        // Get all the memberList where postalCode is null
        defaultMemberShouldNotBeFound("postalCode.specified=false");
    }

    @Test
    @Transactional
    public void getAllMembersByCityIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where city equals to DEFAULT_CITY
        defaultMemberShouldBeFound("city.equals=" + DEFAULT_CITY);

        // Get all the memberList where city equals to UPDATED_CITY
        defaultMemberShouldNotBeFound("city.equals=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    public void getAllMembersByCityIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where city in DEFAULT_CITY or UPDATED_CITY
        defaultMemberShouldBeFound("city.in=" + DEFAULT_CITY + "," + UPDATED_CITY);

        // Get all the memberList where city equals to UPDATED_CITY
        defaultMemberShouldNotBeFound("city.in=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    public void getAllMembersByCityIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where city is not null
        defaultMemberShouldBeFound("city.specified=true");

        // Get all the memberList where city is null
        defaultMemberShouldNotBeFound("city.specified=false");
    }

    @Test
    @Transactional
    public void getAllMembersByProvinceIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where province equals to DEFAULT_PROVINCE
        defaultMemberShouldBeFound("province.equals=" + DEFAULT_PROVINCE);

        // Get all the memberList where province equals to UPDATED_PROVINCE
        defaultMemberShouldNotBeFound("province.equals=" + UPDATED_PROVINCE);
    }

    @Test
    @Transactional
    public void getAllMembersByProvinceIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where province in DEFAULT_PROVINCE or UPDATED_PROVINCE
        defaultMemberShouldBeFound("province.in=" + DEFAULT_PROVINCE + "," + UPDATED_PROVINCE);

        // Get all the memberList where province equals to UPDATED_PROVINCE
        defaultMemberShouldNotBeFound("province.in=" + UPDATED_PROVINCE);
    }

    @Test
    @Transactional
    public void getAllMembersByProvinceIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where province is not null
        defaultMemberShouldBeFound("province.specified=true");

        // Get all the memberList where province is null
        defaultMemberShouldNotBeFound("province.specified=false");
    }

    @Test
    @Transactional
    public void getAllMembersByCountryIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where country equals to DEFAULT_COUNTRY
        defaultMemberShouldBeFound("country.equals=" + DEFAULT_COUNTRY);

        // Get all the memberList where country equals to UPDATED_COUNTRY
        defaultMemberShouldNotBeFound("country.equals=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    public void getAllMembersByCountryIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where country in DEFAULT_COUNTRY or UPDATED_COUNTRY
        defaultMemberShouldBeFound("country.in=" + DEFAULT_COUNTRY + "," + UPDATED_COUNTRY);

        // Get all the memberList where country equals to UPDATED_COUNTRY
        defaultMemberShouldNotBeFound("country.in=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    public void getAllMembersByCountryIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where country is not null
        defaultMemberShouldBeFound("country.specified=true");

        // Get all the memberList where country is null
        defaultMemberShouldNotBeFound("country.specified=false");
    }

    @Test
    @Transactional
    public void getAllMembersByVoteIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where vote equals to DEFAULT_VOTE
        defaultMemberShouldBeFound("vote.equals=" + DEFAULT_VOTE);

        // Get all the memberList where vote equals to UPDATED_VOTE
        defaultMemberShouldNotBeFound("vote.equals=" + UPDATED_VOTE);
    }

    @Test
    @Transactional
    public void getAllMembersByVoteIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where vote in DEFAULT_VOTE or UPDATED_VOTE
        defaultMemberShouldBeFound("vote.in=" + DEFAULT_VOTE + "," + UPDATED_VOTE);

        // Get all the memberList where vote equals to UPDATED_VOTE
        defaultMemberShouldNotBeFound("vote.in=" + UPDATED_VOTE);
    }

    @Test
    @Transactional
    public void getAllMembersByVoteIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where vote is not null
        defaultMemberShouldBeFound("vote.specified=true");

        // Get all the memberList where vote is null
        defaultMemberShouldNotBeFound("vote.specified=false");
    }

    @Test
    @Transactional
    public void getAllMembersByOervIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where oerv equals to DEFAULT_OERV
        defaultMemberShouldBeFound("oerv.equals=" + DEFAULT_OERV);

        // Get all the memberList where oerv equals to UPDATED_OERV
        defaultMemberShouldNotBeFound("oerv.equals=" + UPDATED_OERV);
    }

    @Test
    @Transactional
    public void getAllMembersByOervIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where oerv in DEFAULT_OERV or UPDATED_OERV
        defaultMemberShouldBeFound("oerv.in=" + DEFAULT_OERV + "," + UPDATED_OERV);

        // Get all the memberList where oerv equals to UPDATED_OERV
        defaultMemberShouldNotBeFound("oerv.in=" + UPDATED_OERV);
    }

    @Test
    @Transactional
    public void getAllMembersByOervIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where oerv is not null
        defaultMemberShouldBeFound("oerv.specified=true");

        // Get all the memberList where oerv is null
        defaultMemberShouldNotBeFound("oerv.specified=false");
    }

    @Test
    @Transactional
    public void getAllMembersByCommentIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where comment equals to DEFAULT_COMMENT
        defaultMemberShouldBeFound("comment.equals=" + DEFAULT_COMMENT);

        // Get all the memberList where comment equals to UPDATED_COMMENT
        defaultMemberShouldNotBeFound("comment.equals=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    public void getAllMembersByCommentIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where comment in DEFAULT_COMMENT or UPDATED_COMMENT
        defaultMemberShouldBeFound("comment.in=" + DEFAULT_COMMENT + "," + UPDATED_COMMENT);

        // Get all the memberList where comment equals to UPDATED_COMMENT
        defaultMemberShouldNotBeFound("comment.in=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    public void getAllMembersByCommentIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where comment is not null
        defaultMemberShouldBeFound("comment.specified=true");

        // Get all the memberList where comment is null
        defaultMemberShouldNotBeFound("comment.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultMemberShouldBeFound(String filter) throws Exception {
        restMemberMockMvc.perform(get("/api/members?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(member.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].sex").value(hasItem(DEFAULT_SEX.toString())))
            .andExpect(jsonPath("$.[*].birthdate").value(hasItem(DEFAULT_BIRTHDATE.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].entryDate").value(hasItem(DEFAULT_ENTRY_DATE.toString())))
            .andExpect(jsonPath("$.[*].terminationDate").value(hasItem(DEFAULT_TERMINATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].exitDate").value(hasItem(DEFAULT_EXIT_DATE.toString())))
            .andExpect(jsonPath("$.[*].streetAddress").value(hasItem(DEFAULT_STREET_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].province").value(hasItem(DEFAULT_PROVINCE.toString())))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].vote").value(hasItem(DEFAULT_VOTE.booleanValue())))
            .andExpect(jsonPath("$.[*].oerv").value(hasItem(DEFAULT_OERV.booleanValue())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultMemberShouldNotBeFound(String filter) throws Exception {
        restMemberMockMvc.perform(get("/api/members?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingMember() throws Exception {
        // Get the member
        restMemberMockMvc.perform(get("/api/members/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMember() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);
        int databaseSizeBeforeUpdate = memberRepository.findAll().size();

        // Update the member
        Member updatedMember = memberRepository.findOne(member.getId());
        // Disconnect from session so that the updates on updatedMember are not directly saved in db
        em.detach(updatedMember);
        updatedMember
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .title(UPDATED_TITLE)
            .sex(UPDATED_SEX)
            .birthdate(UPDATED_BIRTHDATE)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .entryDate(UPDATED_ENTRY_DATE)
            .terminationDate(UPDATED_TERMINATION_DATE)
            .exitDate(UPDATED_EXIT_DATE)
            .streetAddress(UPDATED_STREET_ADDRESS)
            .postalCode(UPDATED_POSTAL_CODE)
            .city(UPDATED_CITY)
            .province(UPDATED_PROVINCE)
            .country(UPDATED_COUNTRY)
            .vote(UPDATED_VOTE)
            .oerv(UPDATED_OERV)
            .comment(UPDATED_COMMENT);
        MemberDTO memberDTO = memberMapper.toDto(updatedMember);

        restMemberMockMvc.perform(put("/api/members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(memberDTO)))
            .andExpect(status().isOk());

        // Validate the Member in the database
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeUpdate);
        Member testMember = memberList.get(memberList.size() - 1);
        assertThat(testMember.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testMember.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testMember.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testMember.getSex()).isEqualTo(UPDATED_SEX);
        assertThat(testMember.getBirthdate()).isEqualTo(UPDATED_BIRTHDATE);
        assertThat(testMember.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testMember.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testMember.getEntryDate()).isEqualTo(UPDATED_ENTRY_DATE);
        assertThat(testMember.getTerminationDate()).isEqualTo(UPDATED_TERMINATION_DATE);
        assertThat(testMember.getExitDate()).isEqualTo(UPDATED_EXIT_DATE);
        assertThat(testMember.getStreetAddress()).isEqualTo(UPDATED_STREET_ADDRESS);
        assertThat(testMember.getPostalCode()).isEqualTo(UPDATED_POSTAL_CODE);
        assertThat(testMember.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testMember.getProvince()).isEqualTo(UPDATED_PROVINCE);
        assertThat(testMember.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testMember.isVote()).isEqualTo(UPDATED_VOTE);
        assertThat(testMember.isOerv()).isEqualTo(UPDATED_OERV);
        assertThat(testMember.getComment()).isEqualTo(UPDATED_COMMENT);
    }

    @Test
    @Transactional
    public void updateNonExistingMember() throws Exception {
        int databaseSizeBeforeUpdate = memberRepository.findAll().size();

        // Create the Member
        MemberDTO memberDTO = memberMapper.toDto(member);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMemberMockMvc.perform(put("/api/members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(memberDTO)))
            .andExpect(status().isCreated());

        // Validate the Member in the database
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMember() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);
        int databaseSizeBeforeDelete = memberRepository.findAll().size();

        // Get the member
        restMemberMockMvc.perform(delete("/api/members/{id}", member.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Member.class);
        Member member1 = new Member();
        member1.setId(1L);
        Member member2 = new Member();
        member2.setId(member1.getId());
        assertThat(member1).isEqualTo(member2);
        member2.setId(2L);
        assertThat(member1).isNotEqualTo(member2);
        member1.setId(null);
        assertThat(member1).isNotEqualTo(member2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MemberDTO.class);
        MemberDTO memberDTO1 = new MemberDTO();
        memberDTO1.setId(1L);
        MemberDTO memberDTO2 = new MemberDTO();
        assertThat(memberDTO1).isNotEqualTo(memberDTO2);
        memberDTO2.setId(memberDTO1.getId());
        assertThat(memberDTO1).isEqualTo(memberDTO2);
        memberDTO2.setId(2L);
        assertThat(memberDTO1).isNotEqualTo(memberDTO2);
        memberDTO1.setId(null);
        assertThat(memberDTO1).isNotEqualTo(memberDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(memberMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(memberMapper.fromId(null)).isNull();
    }
}
