@extends('public.layout.main')

@section('title', __('userdash.payment.title'))

@section('content')
    <!-- Payment -->
    <section class="my-5 py-5">
        <div class="container text-center mt-3 pt-5">
            @if (Session::has('success_status'))
                <p class="text-center alert alert-success">{{ Session::get('success_status') }}</p>
            @endif

            <h2 class="font-weight-bold">{{ __('userdash.payment.content.0') }}</h2>
            <hr class="mx-auto">
        </div>

        <div class="mx-auto container text-center">
            @if ($order->status->id == 1 /* i.e not paid  */)
                <p>{{ __('userdash.payment.content.1') . " $ $order->cost" }}</p>
                <!-- <input class="btn btn-primary" type="submit" value="Pay Now"> -->

                @php
                    $website_commission = number_format($order->cost * 0.1, 2, thousands_separator: '');
                    $seller_amount = number_format($order->cost - $website_commission, 2, thousands_separator: '');
                @endphp
                <!-- Set up a container element for the button -->
                <div id="paypal-button-container"></div>
            @else
                <p>{{ __('userdash.payment.content.2') }}</p>
            @endif
        </div>
    </section>

    <form action="{{ route('user.completepayment') }}" id="complete-payment" method="post"></form>
@endsection

@section('js')
    <!-- Include the PayPal JavaScript SDK; replace "test" with your own sandbox Business account app client ID -->
    <script src="https://www.paypal.com/sdk/js?&client-id=CLIENTID&currency=USD&merchant-id=*" data-merchant-id="ID1,ID2">
    </script>

    <script>
        paypal.Buttons({

            // Sets up the transaction when a payment button is clicked
            createOrder: function(data, actions) {
                return actions.order.create({
                    purchase_units: [{
                        reference_id: "REFID-0",
                        amount: {
                            value: "{{ $website_commission }}" // Can reference variables or functions. Example: `value: document.getElementById('...').value`
                        }
                    }, {
                        reference_id: "REFID-1",
                        payee: {
                            email_address: "shoppingcart_25629322@business.example.com",
                        },
                        amount: {
                            value: "{{ $seller_amount }}", // currency_code: "USD",
                        }
                    }]
                });
            },

            // Finalize the transaction after payer approval
            onApprove: function(data, actions) {
                return actions.order.capture().then(function(orderData) {
                    // Successful capture! For dev/demo purposes:
                    console.log('Capture result', orderData, JSON.stringify(orderData, null, 2));

                    var transaction = orderData.purchase_units[0].payments.captures[0];
                    alert("Transaction " + transaction.status + ": " + transaction.id +
                        "\n\n{{ __('userdash.payment.content.3') }}");

                    /* <?php ob_start();
                    echo csrf_field(); ?>
                    <input type="hidden" name="order_id" value="{{ $order->id }}">
                    <?php $form = ob_get_clean(); ?> */

                    var formSelector = "form#complete-payment";
                    var formText = `<?= $form ?> \n<input type="hidden" name="transaction_id"`;
                    formText += ' value="' + transaction.id + '">';

                    document.querySelector(formSelector).innerHTML = formText;
                    document.querySelector(formSelector).submit();
                });
            }
        }).render('#paypal-button-container');
    </script>
@endsection
