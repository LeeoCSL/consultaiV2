package br.com.consultai.activities;

import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.githang.stepview.StepView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.consultai.R;
import br.com.consultai.adapter.ViewPagerAdapter;
import br.com.consultai.fragments.ContaFragment;
import br.com.consultai.fragments.HomeFragment;
import br.com.consultai.fragments.fragmentsCompra.BandeiraFragment;
import br.com.consultai.fragments.fragmentsCompra.BilheteCadastradoFragment;
import br.com.consultai.fragments.fragmentsCompra.CadastrarBilheteFragment;
import br.com.consultai.fragments.fragmentsCompra.CadastrarCreditoFragment;
import br.com.consultai.fragments.fragmentsCompra.ComprarFragment;
import br.com.consultai.fragments.fragmentsCompra.PedidoBoletoFragment;
import br.com.consultai.fragments.fragmentsCompra.PedidoFinalizadoFragment;
import br.com.consultai.fragments.fragmentsCompra.PedidosFragment;
import br.com.consultai.fragments.fragmentsCompra.SelecBancoFragment;
import br.com.consultai.fragments.fragmentsCompra.SelecCreditoFragment;
import br.com.consultai.fragments.fragmentsCompra.TransferenciaFinalizadaFragment;
import br.com.consultai.fragments.fragmentsCompra.TransferenciaFragment;

public class ComprarActivity extends AppCompatActivity {
    StepView mStepView;

    public static ViewPager mViewPager;

//    Button testebtn;

    int i = 1;

    private ComprarFragment mComprarFragment;
    private BandeiraFragment mBandeiraFragment;
    private SelecCreditoFragment mSelecCreditoFragment;
    private CadastrarCreditoFragment mCadastrarCreditoFragment;
    private PedidoFinalizadoFragment mPedidoFinalizadoFragment;
    private BilheteCadastradoFragment mBilheteCadastradoFragment;
    private CadastrarBilheteFragment mCadastrarBilheteFragment;
    private PedidoBoletoFragment mPedidoBoletoFragment;
    private PedidosFragment mPedidosFragment;
    private SelecBancoFragment mSelecBancoFragment;
    private TransferenciaFinalizadaFragment mTransferenciaFinalizadaFragment;
    private TransferenciaFragment mTransferenciaFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comprar);

//        testebtn = (Button) findViewById(R.id.testebtn);

        mStepView = (StepView) findViewById(R.id.step_view);
        List<String> steps = Arrays.asList(new String[]{" ", " ", " "});
        mStepView.setSteps(steps);

        mStepView.selectedStep(1);

        mViewPager = (ViewPager)findViewById(R.id.viewpager);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewPager.setCurrentItem(0);
        setupViewPager(mViewPager);





//        testebtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (i == 1) {
//                    mStepView.selectedStep(2);
//                    i = 2;
//
//                } else if (i == 2) {
//                    mStepView.selectedStep(3);
//                    i = 3;
//                } else if (i == 3) {
//                    mStepView.selectedStep(1);
//                    i = 1;
//                }
//            }
//        });

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        mComprarFragment = new ComprarFragment();
        mBandeiraFragment = new BandeiraFragment();
        mSelecCreditoFragment = new SelecCreditoFragment();
        mCadastrarCreditoFragment = new CadastrarCreditoFragment();
        mPedidoFinalizadoFragment = new PedidoFinalizadoFragment();
        mPedidoFinalizadoFragment = new PedidoFinalizadoFragment();
        mPedidoBoletoFragment = new PedidoBoletoFragment();
        mSelecBancoFragment = new SelecBancoFragment();

        adapter.addFragment(mComprarFragment); // 0
        adapter.addFragment(mBandeiraFragment); // 1
        adapter.addFragment(mSelecCreditoFragment); // 2
        adapter.addFragment(mCadastrarCreditoFragment); // 3
        adapter.addFragment(mPedidoFinalizadoFragment); // 4
        adapter.addFragment(mPedidoBoletoFragment); //5
        adapter.addFragment(mSelecBancoFragment); //6


        viewPager.setAdapter(adapter);
    }

}
