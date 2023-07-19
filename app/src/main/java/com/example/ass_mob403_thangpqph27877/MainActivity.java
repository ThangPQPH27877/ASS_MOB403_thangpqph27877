package com.example.ass_mob403_thangpqph27877;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ProductAdapter.Callback {
    private RecyclerView idRcv;
    private ProductAdapter productAdapter;
    private List<ProductModel> productList;
    private FloatingActionButton btnShowDialogAddProduct;

    private EditText edTen;
    private EditText edGia;
    private EditText edMota;
    private Button btnThem;
    private Button btnHuy;
    private Button btnSua;
    private Button btnXoa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        idRcv = (RecyclerView) findViewById(R.id.id_rcv);
        btnShowDialogAddProduct = (FloatingActionButton) findViewById(R.id.btn_showDialogAddProduct);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        idRcv.setLayoutManager(linearLayoutManager);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        idRcv.addItemDecoration(itemDecoration);
        btnShowDialogAddProduct.setOnClickListener(this);
        ShowProduct();

    }

    private void ShowProduct() {
        Call<List<ProductModel>> call = ApiService.apiService.getProducts();
        call.enqueue(new Callback<List<ProductModel>>() {
            @Override
            public void onResponse(Call<List<ProductModel>> call, Response<List<ProductModel>> response) {
                if (response.isSuccessful()) {
                    List<ProductModel> productList = response.body();
                    showList(productList);
                } else {
                    Log.e("API Error", "Failed to fetch products");
                }
            }

            @Override
            public void onFailure(Call<List<ProductModel>> call, Throwable t) {
                Log.e("API Error", t.getMessage());
            }
        });
    }

    private void showList(List<ProductModel> list) {
        productAdapter = new ProductAdapter(list, this);
        idRcv.setAdapter(productAdapter);
    }

    private void AddProduct(String name, int price, String description) {
        ProductModel model = new ProductModel();
        model.setName(name);
        model.setPrice(price);
        model.setDescription(description);
        Call<ProductModel> call = ApiService.apiService.addProduct(model);
        call.enqueue(new Callback<ProductModel>() {
            @Override
            public void onResponse(Call<ProductModel> call, Response<ProductModel> response) {
                if (response.isSuccessful()) {
                    ProductModel model1 = response.body();
                    ShowProduct();
                } else {
                    Log.e("API Error", "Failed to fetch products");
                }
            }

            @Override
            public void onFailure(Call<ProductModel> call, Throwable t) {
                Log.e("API Error", t.getMessage());
            }
        });
    }

    private void UpdateProduct(String id, String name, int price, String description) {
        ProductModel model = new ProductModel();
        model.setName(name);
        model.setPrice(price);
        model.setDescription(description);
        Call<ProductModel> call = ApiService.apiService.updateProduct(model, id);
        call.enqueue(new Callback<ProductModel>() {
            @Override
            public void onResponse(Call<ProductModel> call, Response<ProductModel> response) {
                if (response.isSuccessful()) {
                    ProductModel model1 = response.body();
                    Toast.makeText(MainActivity.this, "Sửa thành công!!!", Toast.LENGTH_SHORT).show();
                    ShowProduct();
                } else {
                    Toast.makeText(MainActivity.this, "Sửa thất bại!!!", Toast.LENGTH_SHORT).show();
                    Log.e("API Error", "Failed to fetch products");
                }
            }

            @Override
            public void onFailure(Call<ProductModel> call, Throwable t) {
                Log.e("API Error", t.getMessage());
            }
        });

    }

    private void DeleteProduct(String id) {
        Call<Void> call = ApiService.apiService.deleteProduct(id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Xóa Thành công", Toast.LENGTH_SHORT).show();
                    ShowProduct();
                } else {
                    Toast.makeText(MainActivity.this, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                    Log.e("API Error", "Failed to fetch products");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("API Error", t.getMessage());
            }
        });

    }

    @Override
    public void onClick(View view) {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_add);
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (dialog != null && dialog.getWindow() != null){
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        edTen = (EditText) dialog.findViewById(R.id.ed_ten);
        edGia = (EditText) dialog.findViewById(R.id.ed_gia);
        edMota = (EditText) dialog.findViewById(R.id.ed_mota);
        btnThem = (Button) dialog.findViewById(R.id.btn_them);
        btnHuy = (Button) dialog.findViewById(R.id.btn_huy);
        btnThem.setOnClickListener(v ->{
            String name = edTen.getText().toString().trim();
            int price = Integer.parseInt(edGia.getText().toString().trim());
            String description = edMota.getText().toString().trim();
            AddProduct(name, price, description);
            dialog.dismiss();
        });
        btnHuy.setOnClickListener(v ->{
            dialog.dismiss();
        });
        dialog.show();
    }

    @Override
    public void Edit(ProductModel productModel) {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_sua_product);
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (dialog != null && dialog.getWindow() != null){
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        edTen = (EditText) dialog.findViewById(R.id.ed_ten);
        edGia = (EditText) dialog.findViewById(R.id.ed_gia);
        edMota = (EditText) dialog.findViewById(R.id.ed_mota);
        btnSua = (Button) dialog.findViewById(R.id.btn_sua);
        btnHuy = (Button) dialog.findViewById(R.id.btn_huy);
        edTen.setText(productModel.getName());
        edGia.setText(String.valueOf(productModel.getPrice()));
        edMota.setText(productModel.getDescription());
        btnSua.setOnClickListener(v ->{
            String name = edTen.getText().toString().trim();
            int price = Integer.parseInt(edGia.getText().toString().trim());
            String description = edMota.getText().toString().trim();
            UpdateProduct(productModel.getId(), name, price, description);
            dialog.dismiss();
        });
        btnHuy.setOnClickListener(v ->{
            dialog.dismiss();
        });
        dialog.show();
    }

    @Override
    public void Delete(ProductModel productModel) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xóa sản phẩm");
        builder.setMessage("Bạn có chắc chắn muốn xóa sản phẩm này?");
        builder.setPositiveButton("Xóa", (dialog, which) -> {
            DeleteProduct(productModel.getId());
        });
        builder.setNegativeButton("Hủy", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}