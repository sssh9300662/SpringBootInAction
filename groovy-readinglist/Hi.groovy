@RestController
class WebApp{
	@RequestMapping("/hi")
	String greetings(){
		"Spring Boot Rocks!!"
	}
}