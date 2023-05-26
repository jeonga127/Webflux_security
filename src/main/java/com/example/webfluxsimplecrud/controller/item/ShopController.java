//package com.example.webfluxsimplecrud.controller;
//
//import com.example.webfluxsimplecrud.domain.item.Cart;
//import com.example.webfluxsimplecrud.domain.item.Item;
//import com.example.webfluxsimplecrud.dto.item.CartRequestDto;
//import com.example.webfluxsimplecrud.dto.item.ItemRequestDto;
//import com.example.webfluxsimplecrud.service.CartService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.*;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//@RestController
//@RequiredArgsConstructor
//public class ShopController {
//
//    private final CartService cartService;
//
//    @GetMapping("/item")
//    public Flux<Item> getAllItems() {
//        return cartService.getAllItems();
//    }
//
//    @GetMapping("/item/{item}")
//    public Mono<Item> getOneItem(@PathVariable("item") String item){
//        return cartService.getOneItems(item);
//    }
//
//    @GetMapping("/cart")
//    public Mono<Cart> getMyCart(@RequestParam("cart") String cartId) {
//        return cartService.getMyCart(cartId);
//    }
//
//    @PostMapping("/mart")
//    public Mono<Item> createItem(@RequestBody ItemRequestDto itemRequestDto) {
//        return cartService.createItem(itemRequestDto);
//    }
//
//    @PostMapping("/cart")
//    public Mono<Cart> createCart(@RequestBody CartRequestDto cartRequestDto){
//        return cartService.createCart(cartRequestDto);
//    }
//
//    @PostMapping("/cart/{item}")
//    public Mono<Cart> addItemToCart(@RequestParam("cart") String cartId, @PathVariable("item") String item, @RequestParam("quantity") int quantity) {
//        return cartService.addItemToCart(cartId, item, quantity);
//    }
//
////
////    @PutMapping("/cart/item/{item}")
////    public Mono<Cart> removeItemFromCart(@RequestParam("cart") String cartId, @PathVariable("item") String item, @RequestParam("quantity") int quantity) {
////        return cartService.removeItemFromCart(cartId, item, quantity);
////    }
////
////    @DeleteMapping("/item")
////    public Mono<Void> deleteAllItems() {
////        return cartService.deleteAllItems();
////    }
////
////    @DeleteMapping("/cart")
////    public Mono<Cart> deleteAllItemsFromCart(@RequestParam("cart") String cartId) {
////        return cartService.deleteAllItemsFromCart(cartId);
////    }
//}
