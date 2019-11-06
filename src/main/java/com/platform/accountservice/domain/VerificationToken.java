package com.platform.accountservice.domain;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;

import javax.persistence.*;

@Entity
@Table(name = "verification_token")
public class VerificationToken {
    private static final int EXPIRATION = 60 * 24;
 
    @Id
	@Column(name="verification_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer verificationId;
     
    @Column(nullable = false)
    private String token;
   
    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;
     
    @Column(nullable = false, name="expiry_date")
    private Date expiryDate;

    public VerificationToken(){
		this.expiryDate = calculateExpiryDate();
	}
    
    private Date calculateExpiryDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, VerificationToken.EXPIRATION);
        return new Date(cal.getTime().getTime());
    }

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}
}
