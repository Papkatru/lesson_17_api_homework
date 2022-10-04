public class BodyReqresRequests {
    public String email;
    public String password;
    public String name;
    public String job;

    public BodyReqresRequests(String email ,String password) {
        this.email = email;
        this.password = password;
    }

    public BodyReqresRequests(String email) {
        this.email = email;
    }

    public BodyReqresRequests() {
    }

    public BodyReqresRequests setName(String name) {
        this.name = name;
        return this;
    }

    public BodyReqresRequests setJob(String job) {
        this.job = job;
        return this;
    }
}
