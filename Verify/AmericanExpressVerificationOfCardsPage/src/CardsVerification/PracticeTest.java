package CardsVerification;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class PracticeTest {
	ExtentReports report;
	ExtentTest test;
	WebDriver d=new FirefoxDriver();
	String reportFile;
	List<WebElement>cards=new ArrayList<>();
	

	@BeforeTest
	 public  void reportFileBirth() throws Throwable{	
	    	
	    		
	    	    File temp = File.createTempFile("i-am-The-Report", ".html" );
	        	
	    	    reportFile = temp.getAbsolutePath();
	       
	   
	    	
	    }
	
	@Test
	public void multipleVerification() throws Throwable{
		
		d.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		
		d.manage().window().maximize();
		
		report = new ExtentReports(reportFile);

		test=report.startTest("Cards Page Verification");
		
		d.get("https://www.americanexpress.com/us/credit-cards/compare/25330?linknav=ProspectHP-ViewAllCardsLink");
		
		
		
		
		cards=d.findElements(By.xpath("//div[@id='amex-cards-list']/div/div"));
		
		
		for(int i=1;i<cards.size()-2;i++){
		
			String cardTitle=d.findElement(By.xpath("(//div[@id='amex-cards-list']/div[1]/div/div[2]/h2)["+i+"]")).getText();
		
			System.out.println(cardTitle);
			
			String cardImageTitle=d.findElement(By.xpath("(//div[@id='amex-cards-list']/div/div/div[2]/div/div[1]/a)["+i+"]")).getAttribute("title");
		
			System.out.println(cardImageTitle);
			
			if(cardImageTitle.contains(cardTitle.subSequence(1, 4))){
			
				test.log(LogStatus.PASS, "Verified Title : Card Image has been verified with Card Title, They are in its place--- Card Title:- > "+cardTitle+"");
			
				test.log(LogStatus.PASS, "Varified Card Image, Image is Present and at the right Location-> "+cardTitle);
			
				System.out.println("Passed test # "+i);
			
			}else{
			
				test.log(LogStatus.FAIL, "Verification Failed : Could Not find -"+cardTitle+"");
			
				System.out.println("Failed test # "+i);
			
			}
		
		
			WebElement Apply=d.findElement(By.xpath("(//div[@id='amex-cards-list']/div/div/div[2]/div/div[1]/div/a[2])["+i+"]"));
		
			String apl=Apply.getAttribute("title");
		
			System.out.println(apl);
		
			if(apl.contains("Apply")){
			
				test.log(LogStatus.PASS, "Verification of Apply buttons Existence complete for ->"+cardTitle);
			
				System.out.println("Passed app #"+ i);
		
			}else{
			
				test.log(LogStatus.FAIL, "Apply Button not found");
			
				System.out.println("Failed app #"+ i);
		
			}
		
			String applyLink=Apply.getAttribute("href");
		
			System.out.println("Apply link----"+applyLink);
		
			Apply.click();
		
			Thread.sleep(3000);
		
			String clicked=d.getCurrentUrl();
		
			System.out.println("Current URL after click----"+clicked);
		
			if(clicked.contains(cardTitle.subSequence(1, 4))){
			
				System.out.println("Passed: Apply link accurate");
			
				test.log(LogStatus.PASS, "Apply Link Verified for ->"+cardTitle);
			
		
			}else{
			
				System.out.println("Failed, Link has been compromised");
			
				test.log(LogStatus.FAIL, "Links Do not match --- Bug found");
		
			}
		
			Thread.sleep(2000);
		
			boolean offer=d.findElements(By.xpath(".//*[@id='prospectAppForm']/div[4]/div/div/div[2]/div[3]/div[1]/div/div[2]/h2")).size()>0;
		
			System.out.println(offer);
		
			if(offer){
			
		
				test.log(LogStatus.PASS, "Varified apply link for "+cardTitle );
		
			
			}else{
			
				test.log(LogStatus.FAIL, "Could not Verify");
		
			}
		
			d.navigate().back();
		
			Thread.sleep(3000);
		
	
		}
	
		report.endTest(test);
	
		report.flush();
		d.close();
	}
	@AfterTest
	public void reportGeneration(){
		WebDriver d2=new FirefoxDriver();
		d2.get(reportFile);
	}
	
	
}
