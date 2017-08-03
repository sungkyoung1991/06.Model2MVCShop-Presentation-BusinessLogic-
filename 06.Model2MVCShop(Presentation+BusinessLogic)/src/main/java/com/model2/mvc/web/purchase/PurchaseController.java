package com.model2.mvc.web.purchase;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.purchase.PurchaseService;

//==> 회원관리 Controller
@Controller
public class PurchaseController {

	/// Field
	@Autowired
	@Qualifier("purchaseServiceImpl")
	private PurchaseService purchaseService;
	// setter Method 구현 않음

	public PurchaseController() {
		System.out.println(this.getClass());
	}

	// ==> classpath:config/common.properties ,
	// classpath:config/commonservice.xml 참조 할것
	// ==> 아래의 두개를 주석을 풀어 의미를 확인 할것
	@Value("#{commonProperties['pageUnit']}")
	// @Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;

	@Value("#{commonProperties['pageSize']}")
	// @Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;

	@RequestMapping("/addPurchase.do")
	public ModelAndView addPurchase(@ModelAttribute("purchase") Purchase purchase) throws Exception {

		System.out.println("/addPurchase.do");

		purchase.setDivyDate(purchase.getDivyDate().replaceAll("-", ""));

		purchaseService.addPurchase(purchase);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("purchase", purchase);
		modelAndView.setViewName("forward:/purchase/addPurchase.jsp");

		return modelAndView;
	}

	@RequestMapping("/getPurchase.do")
	public ModelAndView getPurchase(@RequestParam("menu") String menu, @RequestParam("tranNo") int tranNo)
			throws Exception {

		System.out.println("/getPurchase.do");
		// Business Logic

		Purchase purchase = purchaseService.getPurchase(tranNo);

		System.out.println("Controller Purchase Check : " + purchase);
		System.out.println("menu check : " + menu);

		
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("purchase", purchase);
		modelAndView.addObject("menu",menu);
//		modelAndView.setViewName("forward:/purchase/getPurchase.jsp");

		if (menu.equals("manage")) {
			modelAndView.setViewName("forward:/purchase/updatePurchaseView.jsp");
		} else {
			modelAndView.setViewName("forward:/purchase/getPurchase.jsp");
		}
		
		return modelAndView;
	}

	@RequestMapping("/updatePurchaseView.do")
	public ModelAndView updatePurchaseView(@RequestParam("tranNo") int tranNo) throws Exception {

		System.out.println("/updatePurchaseView.do");

		Purchase purchase = purchaseService.getPurchase(tranNo);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("forward:/purchase/updatePurchaseView.jsp");
		modelAndView.addObject("purchase", purchase);

		return modelAndView;
	}

	@RequestMapping("/updatePurchase.do")
	public ModelAndView updatePurchase(
								@ModelAttribute("purchase") Purchase purchase,
									@RequestParam("menu") String menu) throws Exception {

		System.out.println("/updatePurchase.do");
		
		
		System.out.println("update menu Check"+menu);
		
		purchase.setDivyDate(purchase.getDivyDate().replaceAll("-", ""));
		
		purchaseService.updatePurchase(purchase);
		System.out.println("updatePurchase : " + purchase);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("forward:/purchase/updatePurchase.jsp");
		modelAndView.addObject("purchase", purchase);
		modelAndView.addObject("menu", menu);

		return modelAndView;
	}

	@RequestMapping("/listPurchase.do")
	public ModelAndView getPurchaseList(@ModelAttribute("search") Search search, @ModelAttribute("page") Page page, HttpServletRequest request)
			throws Exception {

		System.out.println("/listPurchase.do");

		if (search.getCurrentPage() == 0) {
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		User user = (User)request.getSession().getAttribute("user");

		Map<String, Object> map = purchaseService.getPurchaseList(search,user.getUserId());

		Page resultPage = new Page(search.getCurrentPage(), ((Integer) map.get("totalCount")).intValue(), pageUnit,
				pageSize);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("search", search);
		modelAndView.addObject("list", map.get("list"));
		modelAndView.addObject("resultPage", resultPage);

		modelAndView.setViewName("forward:/purchase/listPurchase.jsp");

		return modelAndView;

	}

}