package ru.maralays.mfa.Entity;


/**
 * @author Camara Alseny
 * @version 1.0
 * @since 27/04/2022
 *
 * use this class to map user's session details
 * like user status
 * define how many device are connected to
 * services
 *
 *
 */

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Entity
@Table(name = "SessionDetails")
@NoArgsConstructor
@AllArgsConstructor
public class SessionDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sessionDetails_id;
    @Column(name="session_status")
    private String sessionStatus;
    /*@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userSessionId",referencedColumnName = "id")
    private Users userSessionId;*/


}
