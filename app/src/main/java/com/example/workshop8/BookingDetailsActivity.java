    package com.example.workshop8;

    import androidx.appcompat.app.AppCompatActivity;
    import android.os.Bundle;
    import android.widget.EditText;

    import com.android.volley.Request;
    import com.android.volley.RequestQueue;
    import com.android.volley.Response;
    import com.android.volley.VolleyError;
    import com.android.volley.VolleyLog;
    import com.android.volley.toolbox.StringRequest;
    import com.android.volley.toolbox.Volley;

    import org.json.JSONArray;
    import org.json.JSONException;
    import org.json.JSONObject;

    public class BookingDetailsActivity extends AppCompatActivity {
        public static final String EXTRA_BOOKING_ID = "bookingID";

        EditText etBookingDetailId, etStartDate, etEndDate, etDescription, etDestination, etPrice, etBookingId, etRegion, etFee, etClass;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_booking_details);
            etBookingDetailId = findViewById(R.id.etBookDetailId);
            etStartDate = findViewById(R.id.etStartDate);
            etEndDate = findViewById(R.id.etEndDate);
            etDescription = findViewById(R.id.etDescription);
            etPrice = findViewById(R.id.etPrice);
            etDestination = findViewById(R.id.etDestination);
            etBookingId = findViewById(R.id.etBookingId);
            etRegion = findViewById(R.id.etRegion);
            etFee = findViewById(R.id.etFee);
            etClass = findViewById(R.id.etClass);
            int bookingId = getIntent().getIntExtra(EXTRA_BOOKING_ID, -1);
            if (bookingId != -1) {
                String url = "http://10.0.2.2:8080/Workshop7-1.0-SNAPSHOT/api/booking/getbookingdetail/" + bookingId;

                RequestQueue requestQueue = Volley.newRequestQueue(this);
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null && !response.isEmpty()) {
                            try {
                                JSONArray bookingDetailsArray = new JSONArray(response);
                                if (bookingDetailsArray.length() > 0) {
                                    JSONObject bookingDetailsJson = bookingDetailsArray.getJSONObject(0);

                                    int bookingDetailId = bookingDetailsJson.getInt("bookingDetailId");
                                    String startDate = bookingDetailsJson.optString("tripStart", "No data available");
                                    String endDate = bookingDetailsJson.optString("tripEnd", "No data available");
                                    String description = bookingDetailsJson.optString("description", "No data available");
                                    String destination = bookingDetailsJson.optString("destination", "No data available");
                                    int price = bookingDetailsJson.optInt("basePrice", 0);
                                    String region = bookingDetailsJson.optString("regionId", "No data available");
                                    String fee = bookingDetailsJson.optString("feeId", "No data available");
                                    String travelClass = bookingDetailsJson.optString("classId", "No data available");
                                    int bookingId = bookingDetailsJson.optInt("bookingId", 0);

                                    etBookingDetailId.setText(String.valueOf(bookingDetailId));
                                    etStartDate.setText(startDate);
                                    etEndDate.setText(endDate);
                                    etDescription.setText(description);
                                    etDestination.setText(destination);
                                    etPrice.setText(String.valueOf(price));
                                    etRegion.setText(region);
                                    etFee.setText(fee);
                                    etClass.setText(travelClass);
                                    etBookingId.setText(String.valueOf(bookingId));
                                } else {
                                    etBookingDetailId.setText("No data available");
                                    etStartDate.setText("No data available");
                                    etEndDate.setText("No data available");
                                    etDescription.setText("No data available");
                                    etDestination.setText("No data available");
                                    etPrice.setText("No data available");
                                    etRegion.setText("No data available");
                                    etFee.setText("No data available");
                                    etClass.setText("No data available");
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            etStartDate.setText("No data available");
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.wtf(error.getMessage(), "utf-8");
                    }
                });

                requestQueue.add(stringRequest);
            } else {
                // aaaaaaaaaa
            }
        }
    }