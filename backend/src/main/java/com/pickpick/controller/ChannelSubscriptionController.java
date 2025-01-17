package com.pickpick.controller;

import com.pickpick.controller.dto.ChannelOrderRequest;
import com.pickpick.controller.dto.ChannelSubscriptionRequest;
import com.pickpick.controller.dto.ChannelSubscriptionResponse;
import com.pickpick.controller.dto.ChannelSubscriptionResponses;
import com.pickpick.service.ChannelSubscriptionService;
import com.pickpick.utils.AuthorizationExtractor;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/channel-subscription")
public class ChannelSubscriptionController {

    private final ChannelSubscriptionService channelSubscriptionService;

    public ChannelSubscriptionController(final ChannelSubscriptionService channelSubscriptionService) {
        this.channelSubscriptionService = channelSubscriptionService;
    }

    @GetMapping
    public ChannelSubscriptionResponses getAllChannels(HttpServletRequest request) {
        String memberId = AuthorizationExtractor.extract(request);
        return new ChannelSubscriptionResponses(
                channelSubscriptionService.findAllOrderByViewOrder(Long.parseLong(memberId))
                        .stream()
                        .map(ChannelSubscriptionResponse::from)
                        .collect(Collectors.toList()));
    }

    @PostMapping
    public void subscribeChannel(HttpServletRequest request,
                                 @RequestBody ChannelSubscriptionRequest subscriptionRequest) {
        String memberId = AuthorizationExtractor.extract(request);
        channelSubscriptionService.save(subscriptionRequest, Long.parseLong(memberId));
    }

    @PutMapping
    public void updateViewOrders(HttpServletRequest request, @RequestBody List<ChannelOrderRequest> orderRequests) {
        String memberId = AuthorizationExtractor.extract(request);
        channelSubscriptionService.updateOrders(orderRequests, Long.parseLong(memberId));
    }

    @DeleteMapping("/{channelId}")
    public void unsubscribeChannel(HttpServletRequest request, @PathVariable Long channelId) {
        String memberId = AuthorizationExtractor.extract(request);
        channelSubscriptionService.delete(channelId, Long.parseLong(memberId));
    }
}
