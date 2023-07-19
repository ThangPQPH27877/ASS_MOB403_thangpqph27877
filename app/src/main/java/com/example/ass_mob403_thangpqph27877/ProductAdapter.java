package com.example.ass_mob403_thangpqph27877;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private List<ProductModel> productList;
    private Callback callback ;

    public ProductAdapter(List<ProductModel> productList, Callback callback) {
        this.callback = callback;
        this.productList = productList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductModel product = productList.get(position);
        if (product == null){
            return;
        }
        holder.tvNameProduct.setText(product.getName());
        holder.tvPriceProduct.setText(String.valueOf(product.getPrice()));
        holder.tvDescriptionProduct.setText(product.getDescription());
        holder.btnSua.setOnClickListener(v ->{
            callback.Edit(product);
        });
        holder.btnXoa.setOnClickListener(v ->{
            callback.Delete(product);
        });
    }

    @Override
    public int getItemCount() {
        return productList==null?0: productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout relyProduct;
        private TextView tvNameProduct;
        private TextView tvPriceProduct;
        private TextView tvDescriptionProduct;
        private Button btnSua;
        private Button btnXoa;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            relyProduct = (RelativeLayout) itemView.findViewById(R.id.rely_product);
            tvNameProduct = (TextView) itemView.findViewById(R.id.tv_nameProduct);
            tvPriceProduct = (TextView) itemView.findViewById(R.id.tv_priceProduct);
            tvDescriptionProduct = (TextView) itemView.findViewById(R.id.tv_descriptionProduct);
            btnSua = (Button) itemView.findViewById(R.id.btn_sua);
            btnXoa = (Button) itemView.findViewById(R.id.btn_xoa);
        }


    }
    public interface Callback{
        void Edit(ProductModel productModel);
        void Delete(ProductModel productModel);
    }

}
