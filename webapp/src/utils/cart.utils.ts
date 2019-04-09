export function marshal(cartArr: any): void {
  const cartArrJson = JSON.stringify(cartArr);

  localStorage.setItem("cart", cartArrJson);
}

export function unmarshal(): any[] {
  const cartStr = localStorage.getItem("cart");

  return JSON.parse(cartStr) || [];
}

export function addToCart(id: any, quantity: any) {
  const cart = unmarshal();
  const index = cart.findIndex(e => e.id === id);

  if (index === -1) {
    cart.push({
      id,
      quantity
    });
  } else {
    if (cart[index].quantity + quantity <= 0) {
      cart.splice(index, 1);
    } else {
      cart[index].quantity = cart[index].quantity + quantity;
    }
  }

  marshal(cart);
}

export function emptyCart() {
  localStorage.removeItem("cart");
}
